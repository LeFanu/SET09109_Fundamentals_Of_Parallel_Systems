package c05

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel



import jcsp.lang.*

class QProducer implements CSProcess { 
	 
  def ChannelOutput put
  def int iterations = 100
  def delay = 10000
  
  void run () {
	def timer = new CSTimer()
    println  "QProducer has started " + new Date().format("HH:mm:ss", TimeZone.getTimeZone('UTC'))
	
    for ( i in 1 .. iterations ) {
      put.write(i)
      timer.sleep (delay)
    }
	put.write(null)
    println "QProducer has finished at " + new Date().format("HH:mm:ss", TimeZone.getTimeZone('UTC'))
  }
}
