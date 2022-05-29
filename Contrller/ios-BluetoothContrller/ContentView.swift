//
//  ContentView.swift
//  Bluetooth Controller
//
//  Created by 郭子涵 on 18.03.2022.
//

import SwiftUI


var state = [0,0,0,0]
struct ContentView: View {
    var body: some View {
        ButtonView()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .previewInterfaceOrientation(.landscapeLeft)
    }
}
