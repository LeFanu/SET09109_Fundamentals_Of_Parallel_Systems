package c3 
 
import jcsp.lang.*

class Negator implements CSProcess {
  
  def ChannelInput inChannel
  def ChannelOutput outChannel
  
  void run () {
    while (true) {
      //output the negative of the input value
      int negative = inChannel.read()
      outChannel.write(negative - (negative*2))

      //      println "Negative is " + negative
//      println "Output negative is " + (negative - (negative*2))
    }
  }
}
