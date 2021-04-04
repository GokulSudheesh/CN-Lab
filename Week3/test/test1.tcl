set ns [new Simulator]

$ns color 1 Blue
$ns color 2 Red

set file1 [open out.tr w]
$ns trace-all $file1

set file2 [open out.nam w]
$ns namtrace-all $file2

proc finish {} {
	global ns file1 file2
	$ns flush-trace
	close $file1
	close $file2
	exit 0 }

#Create six nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]

$ns duplex-link $n0 $n2 2Mb 10ms DropTail
$ns simplex-link $n1 $n2 2Mb 10ms DropTail
$ns duplex-link $n2 $n3 0.3Mb 100ms DropTail
$ns duplex-link $n3 $n4 0.5Mb 40ms DropTail
$ns simplex-link $n3 $n5 0.5Mb 30ms DropTail

$ns duplex-link-op $n0 $n2 orient right-down
$ns simplex-link-op $n1 $n2 orient right-up
$ns duplex-link-op $n2 $n3 orient right
$ns duplex-link-op $n3 $n4 orient right-up
$ns simplex-link-op $n3 $n5 orient right-down

$ns queue-limit $n2 $n3 40

#Set up TCP Connection
set tcp [new Agent/TCP]
$ns attach-agent $n0 $tcp

set sink [new Agent/TCPSink]
$ns attach-agent $n4 $sink
$ns connect $tcp $sink

$tcp set fid_ 1 #Blue color 
$tcp set packetSize_ 552 #TCP Packet Size 552

set ftp [new Application/FTP]
$ftp attach-agent $tcp

#Set up UDP connection
set udp [new Agent/UDP]
$ns attach-agent $n1 $udp
#Agent is superclass and Null is subclass.
set null [new Agent/Null]
$ns attach-agent $n5 $null
$ns connect $udp $null
$udp set fid_ 2 #Red color 

set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set packet_size_ 1000 #CBR Packet Size 1000

# Scheduling the event
$ns at 0.1 "$cbr start"
$ns at 1.0 "$ftp start"
$ns at 624.0 "$ftp stop"
$ns at 624.5 "$cbr stop"
# Call finish procedure
$ns at 625.0 "finish"
# Run the simulation
$ns run