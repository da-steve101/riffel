
#Riffel

The goal of this project is to build a bridge between [Riffa](http://riffa.ucsd.edu) and [Chisel](http://chisel.eecs.berkeley.edu/).

It aims to simplify the PCIe communication between Chisel modules and the host. 

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

Below there is a simple example that echoes the data in input adding one to each value.

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
