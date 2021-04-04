#Create Simulator Object (Simulator is class in ns2)
set ns [new Simulator]
#Define different colors for data flows (for NAM) ($ means reference)
$ns color 1 Blue
$ns color 2 Red
#Open the Event trace files
set file1 [open out.tr w]
#trace-all for capturing event trace.
$ns trace-all $file1
#Open the NAM trace file
set file2 [open out.nam w]
#namtrace-all for capturing animation details.
$ns namtrace-all $file2
#Create six nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]
#Create links between the nodes
#Syntax: $ns duplex-link source destination data-rate propagation-delay queue-type
#DropTail class for simple queue.
$ns duplex-link $n0 $n2 2Mb 10ms DropTail
$ns duplex-link $n1 $n2 2Mb 10ms DropTail
$ns simplex-link $n2 $n3 0.3Mb 100ms DropTail
$ns simplex-link $n3 $n2 0.3Mb 100ms DropTail
$ns duplex-link $n3 $n4 0.5Mb 40ms DropTail
$ns duplex-link $n3 $n5 0.5Mb 30ms DropTail
#Give node position (for NAM)
#Syntax: $ns duplex-link-op source destination orient orientation-position
$ns duplex-link-op $n0 $n2 orient right-down
$ns duplex-link-op $n1 $n2 orient right-up
$ns simplex-link-op $n2 $n3 orient right
$ns simplex-link-op $n3 $n2 orient left
$ns duplex-link-op $n3 $n4 orient right-up
$ns duplex-link-op $n3 $n5 orient right-down
#Set Queue Size of link (n2-n3) to 40
#Syntax: $ns queue-limt source destination queue-size
$ns queue-limit $n2 $n3 40
#Setup a TCP connection (Source agent: TCP, Destination agent: TCPSink)
#Agent is superclass and TCP is subclass.
set tcp [new Agent/TCP]
$ns attach-agent $n0 $tcp
#Agent is superclass and TCPSink is subclass.
set sink [new Agent/TCPSink]
$ns attach-agent $n4 $sink
$ns connect $tcp $sink
$tcp set fid_ 1
$tcp set packetSize_ 552
#Setup a FTP over TCP connection
#Application is superclass and FTP is subclass.
set ftp [new Application/FTP]
$ftp attach-agent $tcp
#Setup a UDP connection (Source agent: UDP, Destination agent: Null)
#Agent is superclass and UDP is subclass.
set udp [new Agent/UDP]
$ns attach-agent $n1 $udp
#Agent is superclass and Null is subclass.
set null [new Agent/Null]
$ns attach-agent $n5 $null
$ns connect $udp $null
$udp set fid_ 2
#Setup a CBR over UDP connection
#CBR(Constant Bit Rate) is subclass of Traffic and Traffic is subclass of Application
set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set packet_size_ 1000
# Scheduling the event
$ns at 0.1 "$cbr start"
$ns at 1.0 "$ftp start"
$ns at 624.0 "$ftp stop"
$ns at 624.5 "$cbr stop"

proc finish {} {
	global ns file1 file2
	$ns flush-trace
	close $file1
	close $file2
	exit 0 }

# Call finish procedure
$ns at 625.0 "finish"
# Run the simulation
$ns run