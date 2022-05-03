//
//  ButtonView.swift
//  Bluetooth Controller
//
//  Created by 郭子涵 on 18.04.2022.
//

import SwiftUI
import CoreBluetooth

struct ButtonView: View {
    var bleHelper = BleHelper.shared
    struct CustomButtonStyle: ButtonStyle {
        var onPressed: () -> Void
        var onReleased: () -> Void
        // Wrapper for isPressed where we can run custom logic via didSet (or willSet)
        @State private var isPressedWrapper: Bool = false {
            didSet {
                // new value is pressed, old value is not pressed -> switching to pressed state
                if (isPressedWrapper && !oldValue) {
                    onPressed()
                }
                // new value is not pressed, old value is not pressed -> switching to unpressed state
                else if (oldValue && !isPressedWrapper) {
                    onReleased()
                }
            }
        }
        
        // return the label unaltered, but add a hook to watch changes in configuration.isPressed
        func makeBody(configuration: Self.Configuration) -> some View {
            return configuration.label
                .onChange(of: configuration.isPressed, perform: { newValue in isPressedWrapper = newValue })
        }
    }
    
    @State var selectedSpeedTag = "Slow"
    
    var body: some View {
        VStack{
            Text("Speed: \(selectedSpeedTag)")
        VStack {
                    Picker("speed Tag", selection: $selectedSpeedTag){
                            Text("Slow").tag("Slow")
                            Text("Medium").tag("Medium")
                            Text("Fast").tag("Fast")
                        }
                    }.pickerStyle(SegmentedPickerStyle())
        }.frame(width: 400, height: 160)
        HStack{
            VStack{
                Button(action: {
                    
                }, label: {
                    Image(systemName: "chevron.up")
                        .frame(width: 150 , height: 150, alignment: .center)
                        .foregroundColor(Color.white)
                        .background(Color.blue)
                        .cornerRadius(90)
                        .padding()
                }).buttonStyle(CustomButtonStyle(onPressed: {
                    if state[1] == 0{
                        switch selectedSpeedTag{
                            case "Medium" :
                                state[0]=2
                            case "Fast":
                                state[0]=3
                            default:
                                state[0]=1
                        }
                    }
                    
                    print(state)
                }, onReleased: {
                    state[0]=0
                    print(state)
                }))
                
                Button(action: {
                                
                }, label: {
                    Image(systemName: "chevron.down")
                        .frame(width: 150 , height: 150, alignment: .center)
                        .foregroundColor(Color.white)
                        .background(Color.blue)
                        .cornerRadius(90)
                        .padding()
                }).buttonStyle(CustomButtonStyle(onPressed: {
                    if state[0] == 0{
                        switch selectedSpeedTag{
                            case "Medium" :
                                state[1]=2
                            case "Fast":
                                state[1]=3
                            default:
                                state[1]=1
                        }
                    }
                    print(state)
                }, onReleased: {
                    state[1]=0
                    print(state)
                }))
                .cornerRadius(90)
                
            }.padding(.horizontal, 200)
            
            HStack{
                Button(action: {
                                
                }, label: {
                    Image(systemName:"chevron.backward")
                        .frame(width: 150 , height: 150, alignment: .center)
                        .foregroundColor(Color.white)
                        .background(Color.blue)
                        .cornerRadius(90)
                        .padding()
                }).buttonStyle(CustomButtonStyle(onPressed: {
                    if state[3] == 0{
                        state[2] = 1
                    }
                    print(state)
                }, onReleased: {
                    state[2]=0
                    print(state)
                }))
                
                
                
                Button(action: {
                    
                }, label: {
                    Image(systemName: "chevron.right")
                        .frame(width: 150 , height: 150, alignment: .center)
                        .foregroundColor(Color.white)
                        .background(Color.blue)
                        .cornerRadius(90)
                        .padding()
                }).buttonStyle(CustomButtonStyle(onPressed: {
                    if state[2] == 0{
                        state[3]=1
                    }
                    print(state)
                }, onReleased: {
                    state[3]=0
                    print(state)
                }))
                
            }.padding(.horizontal, 150)
            
            
        }
    }
}
