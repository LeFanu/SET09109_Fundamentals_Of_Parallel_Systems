package c11

import groovyJCSP.ALT
import jcsp.lang.*

class ControllerUIManager implements CSProcess{

    ChannelInput factorFromScaler
    ChannelOutput suspendToScaler
    ChannelOutput injectToScaler

    ChannelOutput statusOfCommunication
    ChannelOutput originalScalingFactor
    ChannelInput suspendCommunication
    ChannelInput newScalingFactor
    ChannelOutput currentScalingFactor

    void run() {
        def currentFactor = factorFromScaler.read()
        def inputChannelsALT = new ALT([suspendCommunication, newScalingFactor, factorFromScaler])
        while (true) {
            switch (inputChannelsALT.priSelect())
            {
                case 0:
                    suspendCommunication.read()
                    suspendToScaler.write()
                    statusOfCommunication.write("Suspended")
                    currentFactor = factorFromScaler.read()
                    currentScalingFactor.write(currentFactor.toString())
                  break
                case 1:
                    def newFactor = newScalingFactor.read()
                    injectToScaler.write(newFactor)
                    statusOfCommunication.write("Active")
                    break
                case 2:
                    currentFactor = factorFromScaler.read()
                    currentScalingFactor.write(currentFactor.toString())
                    break
            }
        }
    }
}