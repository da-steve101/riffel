
#Riffel

This project will be a bridge through [Riffa](http://riffa.ucsd.edu) project interface to [Chisel](http://chisel.eecs.berkeley.edu/) world.

It aims to simplify a bit more the PCIe communication of Chisel modules to the host. 

To build tests:

```
sbt test:run
```

To generate a ***RiffelBridge***:

```
sbt "run $bitSize $txBufferSize"

#i.e. sbt run 128 20
```
where defaults are:
bitSize = 64
txBufferSize = 10

##How to use this

A sample Riffel usage that echo the data from in to out adding one will looks as follows:
```scala
case class RiffelEchoExample(C_PCI_DATA_WIDTH: Int) extends Module {
	val io = new Bundle {
		val rx_data = UInt(INPUT, width=C_PCI_DATA_WIDTH)
		val rx_data_len = UInt(INPUT, width=32)
		val rx_data_en = Bool(INPUT)

		val tx_data = UInt(OUTPUT, width=C_PCI_DATA_WIDTH)
		val tx_data_len = UInt(OUTPUT, width=32)
		val tx_data_en = Bool(OUTPUT)
	}

	io.tx_data_len <> io.rx_data_len
	io.tx_data_en <> io.rx_data_en
	io.tx_data := io.rx_data + UInt(1, width=C_PCI_DATA_WIDTH)
}
```

###This is still a work in progress

All the basic communication is covered but we do not yet integrate the management of "LAST" bit.