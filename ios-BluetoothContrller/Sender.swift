//
//  Sender.swift
//  BluetoothCar
//
//  Created by 郭子涵 on 26.04.2022.
//

import Foundation
import CoreBluetooth

class sender {
    func sentData(ble:BleHelper){
        while true {
            switch state.description{
            case "[3, 0, 0, 0]":
                self.sent(ble: ble, str: "A")
            case "[2, 0, 0, 0]" :
                self.sent(ble: ble, str: "B")
            case "[1, 0, 0, 0]" :
                self.sent(ble: ble, str: "C")
            //后
            case "[0, 3, 0, 0]" :
                self.sent(ble: ble, str: "D")
            case "[0, 2, 0, 0]" :
                self.sent(ble: ble, str: "E")
            case "[0, 1, 0, 0]" :
                self.sent(ble: ble, str: "F")
            //左前
            case "[3, 0, 1, 0]" :
                self.sent(ble: ble, str: "G")
            case "[2, 0, 1, 0]" :
                self.sent(ble: ble, str: "H")
            case "[1, 0, 1, 0]" :
                self.sent(ble: ble, str: "I")
            //左后
            case "[0, 3, 1, 0]" :
                self.sent(ble: ble, str: "J")
            case "[0, 2, 1, 0]" :
                self.sent(ble: ble, str: "K")
            case "[0, 1, 1, 0]" :
                self.sent(ble: ble, str: "L")
            //右前
            case "[3, 0, 0, 1]" :
                self.sent(ble: ble, str: "M")
            case "[2, 0, 0, 1]" :
                self.sent(ble: ble, str: "N")
            case "[1, 0, 0, 1]" :
                self.sent(ble: ble, str: "O")
            //右后
            case "[0, 3, 0, 1]" :
                self.sent(ble: ble, str: "P")
            case "[0, 2, 0, 1]" :
                self.sent(ble: ble, str: "Q")
            case "[0, 1, 0, 1]" :
                self.sent(ble: ble, str: "R")
            //单轮
            case "[0, 0, 1, 0]" :
                self.sent(ble: ble, str: "S")
            case "[0, 0, 0, 1]" :
                self.sent(ble: ble, str: "T")
            default:
                self.sent(ble: ble, str: "0")
            }
        }
    }
    private func sent(ble:BleHelper,str:String){
        ble.sendPacketWithPieces(data: str.data(using: .ascii) ?? "0".data(using: .ascii)!,peripheral: ble.pe ?? CBPeripheral.value(forKey: "0") as! CBPeripheral , characteristic: ble.writeCh ?? CBCharacteristic.value(forKey: "0") as! CBCharacteristic)
        print(str)
        Thread.sleep(forTimeInterval: 0.7)
    }
}
