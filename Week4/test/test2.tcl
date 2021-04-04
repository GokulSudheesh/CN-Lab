set ns [new Simulator]

$ns color 1 Red

set file1 [open out2.tr w]
$ns trace-all $file1

set file2 [open out2.nam w]
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
set n6 [$ns node]
set n7 [$ns node]

$n7 color red
$n7 shape box


#LAN:
set lan [$ns newLan "$n0 $n1 $n2 $n3 $n4 $n5 $n6" 1Mb 40ms LL Queue/DropTail MAC/Csma/Cd Channel]

#Links:
$ns duplex-link $n7 $n0 1Mb 50ms DropTail

#Set up UDP connection
set udp [new Agent/UDP]
$ns attach-agent $n7 $udp
#Agent is superclass and Null is subclass.
set null [new Agent/Null]
$ns attach-agent $n6 $null
$ns connect $udp $null
$udp set fid_ 1 #Red color 

set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set packet_size_ 1000 #CBR Packet Size 1000
#Rate of trasnmitting CBR packets
$cbr set rate_ 0.01mb

# Scheduling the event
$ns at 0.1 "$cbr start"
$ns at 24.5 "$cbr stop"
# Call finish procedure
$ns at 25.0 "finish"
# Run the simulation
$ns run