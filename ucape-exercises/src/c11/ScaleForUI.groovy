package c11

import c05.ScaledData
import groovyJCSP.ALT
import jcsp.lang.CSProcess
import jcsp.lang.ChannelInput
import jcsp.lang.ChannelOutput

class ScaleForUI implements CSProcess {
  def int scalingFactor
  def int multiplier = 2 //Added here - Karol
  def ChannelOutput outChannel
  def ChannelOutput factor
  def ChannelInput inChannel
  def ChannelInput suspend
  def ChannelInput injector
  
  void run () {
    def SUSPEND  = 0
    def INJECT   = 1
    def INPUT    = 2

    def scaleAlt = new ALT ( [ suspend, injector, inChannel ] )


    def suspended = false
    while (true) {
      factor.write(scalingFactor)
      switch ( scaleAlt.priSelect() ) {
        case SUSPEND :
          println "Suspended"
          suspend.read()          // its a signal, no data content
          factor.write(scalingFactor)   //reply with current value of scaling
          suspended = true


          break

        case INJECT:
          //  deal with inject input
          scalingFactor = injector.read()   //this is the resume signal as well
          scalingFactor -= 48
          println "Injected scaling is $scalingFactor"
          suspended = false
          break


        case INPUT:
          def inputValue = inChannel.read()
          def outputValue = new ScaledData()
          outputValue.original = inputValue
          if (suspended == true) {
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

