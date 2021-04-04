set ns [new Simulator]

# Open tracefile
set tracefile [open out2.tr w]
$ns trace-all $tracefile

set namfile [open out2.nam w]
$ns namtrace-all $namfile

# Define the finish procedure
proc finish {} {
    global ns tracefile
    $ns flush-trace
    close $tracefile
    exit 0
}

# Create the nodes
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
$n6 color blue
$n6 shape box

# Setup LAN
set lan [$ns newLan "$n0 $n1 $n2 $n3 $n4 $n5 $n6" 1Mb 40ms LL Queue/DropTail MAC/Csma/Cd Channel]

# A gateway link
$ns duplex-link $n0 $n7 1.0Mb 50ms DropTail


# Setup a UDP connection
set udp [new Agent/UDP]
$ns attach-agent $n7 $udp
set null [new Agent/Null]

$ns attach-agent $n6 $null
$ns connect $udp $null

# Setup a CBR over UDP connection
set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set packet_size_ 1000

# Scheduling the events
$ns at 0.5 "$cbr start"
$ns at 24.5 "$cbr stop"
$ns at 25.0 "finish"

$ns run
