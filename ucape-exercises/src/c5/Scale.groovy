package c5
     
import jcsp.lang.*
import groovyJCSP.*
import c05.ScaledData     
   
class Scale implements CSProcess {
  def int scalingFactor
  def int multiplier = 2 //Added here - Karol
  def ChannelOutput outChannel
  def ChannelOutput factor
  def ChannelInput inChannel
  def ChannelInput suspend
  def ChannelInput injector
  
  void run () {
    def SECOND = 1000
    def DOUBLE_INTERVAL = 5 * SECOND
    def SUSPEND  = 0
    def INJECT   = 1
    //def TIMER    = 2
    def INPUT    = 2
    //def INPUT    = 3
    
    //def timer = new CSTimer()
    //def scaleAlt = new ALT ( [ suspend, injector, timer, inChannel ] )
    def scaleAlt = new ALT ( [ suspend, injector, inChannel ] )
    
//    def preCon = new boolean [4]
    def preCon = new boolean [3]
    preCon[SUSPEND] = true
    preCon[INJECT] = false
    //preCon[TIMER] = true
    preCon[INPUT] = true
    //def suspended = false
                                                                    
    //def timeout = timer.read() + DOUBLE_INTERVAL
    //timer.setAlarm ( timeout )
    
    while (true) {
      switch ( scaleAlt.select(preCon) ) {
        case SUSPEND :
          //  deal with suspend input
          println "Suspended"
          suspend.read()          // its a signal, no data content
          factor.write(scalingFactor)   //reply with current value of scaling
          preCon[INJECT] = true
          preCon[SUSPEND] = false
          //preCon[TIMER] = false

          break

        case INJECT:
          //  deal with inject input
          scalingFactor = injector.read()   //this is the resume signal as well
          println "Injected scaling is $scalingFactor"
          //timeout = timer.read() + DOUBLE_INTERVAL
          //timer.setAlarm ( timeout )
          preCon[INJECT] = false
          preCon[SUSPEND] = true
          //preCon[TIMER] = true
          break

        /*case TIMER:
          //  deal with Timer input
          timeout = timer.read() + DOUBLE_INTERVAL
          timer.setAlarm ( timeout )
          scalingFactor *= multiplier
          println "Normal Timer: new scalingFactor is ${scalingFactor}"
          break*/

        case INPUT:
          //   deal with Input channel
          def inputValue = inChannel.read()
          def outputValue = new ScaledData()
          outputValue.original = inputValue
          if (preCon[INJECT] == true) {
              outputValue.scaled = inputValue
            }
          else
            {
              outputValue.scaled = inputValue * scalingFactor
            }
          outChannel.write ( outputValue )

          break
      } //end-switch
    } //end-while
  } //end-run

}

