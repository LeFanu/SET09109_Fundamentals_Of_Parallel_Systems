package c11

import groovyJCSP.*
import jcsp.lang.*
import jcsp.awt.*
import java.awt.*

class ControllerInterface implements CSProcess {

    ActiveCanvas controllerWindowCanvas
    ChannelInput statusOfCommunication
    ChannelInput originalScalingFactor
    ChannelOutput suspendCommunication
    ChannelOutput newScalingFactor
    ChannelInput currentScalingFactor

    void run() {
        def dList = new DisplayList()
        def root = new ActiveClosingFrame("Controller User Interface")
        def mainFrame = root.getActiveFrame()
        mainFrame.setSize(800,1000)
        controllerWindowCanvas = new ActiveCanvas()
        controllerWindowCanvas.setPaintable(dList)
        controllerWindowCanvas.setSize(500, 800)


        def communicationStatusLabel = new Label("Communication Suspended")
        communicationStatusLabel.setAlignment(Label.CENTER)
        def communicationValue = new ActiveLabel(statusOfCommunication, "Active")
        communicationValue.setAlignment(Label.CENTER)
        def communicationSuspendButton = new ActiveButton(null, suspendCommunication, "Suspend communication")


        def originalScalingLabel = new Label("Original Scaling Factor")
        originalScalingLabel.setAlignment(Label.CENTER)
        def originalFactor = new ActiveLabel(originalScalingFactor, "2")
        originalFactor.setAlignment(Label.CENTER)

        def newScalingValueLabel = new Label("")
        def newScalingValueInput = new ActiveTextEnterField(null, newScalingFactor, "")

        def currentScalingFactor = new Label("Current scaling factor")
        currentScalingFactor.setAlignment(Label.CENTER)
        def currentScalingFactorValue = new ActiveLabel(this.currentScalingFactor)
        currentScalingFactorValue.setAlignment(Label.CENTER)


        def controllerContainer = new Container()
        controllerContainer.setLayout(new GridLayout(10,1))
        controllerContainer.add(communicationStatusLabel)
        controllerContainer.add(communicationValue)
        controllerContainer.add(communicationSuspendButton)
        controllerContainer.add(originalScalingLabel)
        controllerContainer.add(originalFactor)
        controllerContainer.add(newScalingValueLabel)
        controllerContainer.add(newScalingValueInput.getActiveTextField())
        controllerContainer.add(currentScalingFactor)
        controllerContainer.add(currentScalingFactorValue)

        mainFrame.setLayout(new BorderLayout())
        mainFrame.add(controllerWindowCanvas, BorderLayout.CENTER)
        mainFrame.add(controllerContainer)

        mainFrame.pack()
        mainFrame.setVisible(true)

        def network = [root, controllerWindowCanvas, currentScalingFactorValue, newScalingValueInput, communicationValue, communicationSuspendButton, communicationSuspendButton]
        new PAR(network).run()
    }
}
