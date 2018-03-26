package c11

import groovyJCSP.*
import jcsp.lang.*

class ControllerConnectionForUI implements CSProcess{

    ChannelInput scalingFactorFromScaler
    ChannelOutput suspendToScaler
    ChannelOutput injectToScaler

    void run() {
        def suspendCommunication = Channel.createOne2One()
        def currentScalingFactor = Channel.createOne2One()
        def newScalingFactor = Channel.createOne2One()
        def originalScalingFactor = Channel.createOne2One()
        def statusOfCommunication = Channel.createOne2One()

        def network = [ new ControllerInterface(suspendCommunication: suspendCommunication.out(),
                                                currentScalingFactor: currentScalingFactor.in(),
                                                newScalingFactor: newScalingFactor.out(),
                                                originalScalingFactor:  originalScalingFactor.in(),
                                                statusOfCommunication: statusOfCommunication.in()),
                        new ControllerUIManager(factorFromScaler: scalingFactorFromScaler,
                                                suspendToScaler: suspendToScaler,
                                                injectToScaler: injectToScaler,
                                                currentScalingFactor: currentScalingFactor.out(),
                                                newScalingFactor: newScalingFactor.in(),
                                                suspendCommunication: suspendCommunication.in(),
                                                originalScalingFactor: originalScalingFactor.out(),
                                                statusOfCommunication:  statusOfCommunication.out())]
        new PAR (network).run()
    }
}
