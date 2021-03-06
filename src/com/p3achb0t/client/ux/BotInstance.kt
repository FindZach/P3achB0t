package com.p3achb0t.client.ux

import com.p3achb0t.client.accounts.Account
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.injection.InstanceManager
import com.p3achb0t.client.injection.InstanceManagerInterface
import com.p3achb0t.client.loader.JarLoader
import java.applet.Applet
import java.awt.BorderLayout
import java.util.*
import javax.swing.JPanel


class BotInstance(var account: Account = Account(), var tabBarTextInfo: String = "") : JPanel() {
    // add the canvas to this JPanel

    var sessionToken: String = ""
    private lateinit var applet: Applet
    lateinit var instanceManagerInterface: InstanceManagerInterface

    init {
        this.layout = BorderLayout()
        JarLoader.load(account)?.let {
            applet = it
            sessionToken = account.uuid
            instanceManagerInterface = applet as InstanceManagerInterface
            add(applet) // add the game to the JPanel
            // Strip off any auth if there is any

            val proxy = if(account.proxy == "none") account.proxy else account.proxy.split(";")[1]
            instanceManagerInterface.getManager().account = account
            GlobalStructs.botManager.botTabBar.addBotInstance("$tabBarTextInfo - ${account.username}-${proxy}", sessionToken, this)
        }
    }

    fun kill() {
        applet.destroy()
        GlobalStructs.botManager.botTabBar.killBotInstance(sessionToken)
    }

    fun getInstanceManager(): InstanceManager {
        return instanceManagerInterface.getManager()
    }
}