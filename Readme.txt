cd "/mnt/c/Users/sudhe/Documents/CN Lab"

Run any one form the following to set DISPLAY environment variable:

export Display=0:0

export DISPLAY="`grep nameserver /etc/resolv.conf | sed 's/nameserver //'`:0"

export DISPLAY="`sed -n 's/nameserver //p' /etc/resolv.conf`:0"

Working:
export DISPLAY=$(ip route|awk '/^default/{print $3}'):0.0

Running the following command, it is possible to see that the $DISPLAY environment variable 
now has the Windows Host’s IP set:

echo $DISPLAY