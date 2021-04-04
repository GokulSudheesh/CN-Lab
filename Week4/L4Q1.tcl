#Create Simulator Object (Simulator is class in ns2)
set ns [new Simulator]
#Define different colors for data flows (for NAM) ($ means reference)
$ns color 1 Blue
$ns color 2 Red
#Open the Event trace files
set file1 [open out2.tr w]
$ns trace-all $file1
#Open the NAM trace file
set file2 [open out2.nam w]
$ns namtrace-all $file2

#Create six nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]

$n1 color red
$n1 shape box
$n0 color blue
$n0 shape box

#Create links between the nodes
$ns duplex-link $n0 $n2 2Mb 10ms DropTail
$ns duplex-link $n1 $n2 2Mb 10ms DropTail
$ns duplex-link $n2 $n3 0.3Mb 100ms DropTail

#Create LAN
set lan [$ns newLan "$n3 $n4 $n5" 0.5Mb 40ms LL
Queue/DropTail MAC/Csma/Cd Channel]

#Set Queue Size of link (n2-n3) to 10
$ns queue-limit $n2 $n3 10

#Setup a TCP connection (Source agent: TCP, Destination agent: TCPSink)
set tcp [new Agent/TCP]
$ns attach-agent $n0 $tcp
set sink [new Agent/TCPSink]
$ns attach-agent $n4 $sink
$ns connect $tcp $sink
$tcp set fid_ 1
$tcp set packetSize_ 552
#Setup a FTP over TCP connection
set ftp [new Application/FTP]
$ftp attach-agent $tcp

#Setup a UDP connection (Source agent: UDP, Destination agent: Null)
set udp [new Agent/UDP]
$ns attach-agent $n1 $udp
set null [new Agent/Null]
$ns attach-agent $n5 $null
$ns connect $udp $null
$udp set fid_ 2
#Setup a CBR over UDP connection
set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set packet_size_ 1000

#Define a 'finish' procedure
proc finish {} {
	global ns file1 file2
	$ns flush-trace
	close $file1
	close $file2
	exit 0 }

# Scheduling the event
$ns at 0.1 "$cbr start"
$ns at 1.0 "$ftp start"
$ns at 124.0 "$ftp stop"
$ns at 124.5 "$cbr stop"
# Call finish procedure
$ns at 125.0 "finish"
# Run the simulation
$ns run