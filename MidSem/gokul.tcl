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

set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]
set n6 [$ns node]
set n7 [$ns node]
set n8 [$ns node]
set n9 [$ns node]
set n10 [$ns node]
set n11 [$ns node]

$ns duplex-link $n0 $n1 1Mb 50ms DropTail
$ns duplex-link $n0 $n4 1Mb 50ms DropTail
$ns duplex-link $n1 $n2 1Mb 50ms DropTail
$ns duplex-link $n1 $n5 1Mb 50ms DropTail
$ns duplex-link $n2 $n3 1Mb 50ms DropTail
$ns duplex-link $n2 $n6 1Mb 50ms DropTail
$ns duplex-link $n3 $n7 1Mb 50ms DropTail
$ns duplex-link $n7 $n6 1Mb 50ms DropTail
$ns duplex-link $n7 $n11 1Mb 50ms DropTail
$ns duplex-link $n6 $n5 1Mb 50ms DropTail
$ns duplex-link $n6 $n10 1Mb 50ms DropTail
$ns duplex-link $n5 $n9 1Mb 50ms DropTail
$ns duplex-link $n5 $n4 1Mb 50ms DropTail
$ns duplex-link $n4 $n8 1Mb 50ms DropTail
$ns duplex-link $n8 $n9 1Mb 50ms DropTail
$ns duplex-link $n9 $n10 1Mb 50ms DropTail
$ns duplex-link $n10 $n11 1Mb 50ms DropTail

$ns duplex-link-op $n0 $n1 orient right
$ns duplex-link-op $n0 $n4 orient down
$ns duplex-link-op $n1 $n2 orient right
$ns duplex-link-op $n1 $n5 orient down
$ns duplex-link-op $n2 $n3 orient right
$ns duplex-link-op $n2 $n6 orient down
$ns duplex-link-op $n3 $n7 orient down
$ns duplex-link-op $n4 $n5 orient right
$ns duplex-link-op $n4 $n8 orient down
$ns duplex-link-op $n5 $n6 orient right
$ns duplex-link-op $n5 $n9 orient down
$ns duplex-link-op $n6 $n7 orient right
$ns duplex-link-op $n6 $n10 orient down
$ns duplex-link-op $n7 $n11 orient down
$ns duplex-link-op $n8 $n9 orient right
$ns duplex-link-op $n9 $n10 orient right
$ns duplex-link-op $n10 $n11 orient right

$ns queue-limit $n0 $n1 40
$ns queue-limit $n8 $n9 40

#Setting TCP:
set tcp [new Agent/TCP]
$ns attach-agent $n0 $tcp
set sink [new Agent/TCPSink]
$ns attach-agent $n11 $sink
$ns connect $tcp $sink
$tcp set fid_ 1
$tcp set packetSize_ 400

set ftp [new Application/FTP]
$ftp attach-agent $tcp

#Setting UDP:
set udp [new Agent/UDP]
$ns attach-agent $n8 $udp
set null [new Agent/Null]
$ns attach-agent $n3 $null
$ns connect $udp $null
$udp set fid_ 2

set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set rate_ 0.1mb
$cbr set packet_size_ 800

$ns at 0.1 "$cbr start"
$ns at 1 "$ftp start"
$ns at 49 "$ftp stop"
$ns at 49.5 "$cbr stop"
$ns at 50 "finish"
$ns run
