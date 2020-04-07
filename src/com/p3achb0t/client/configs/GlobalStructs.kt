package com.p3achb0t.client.configs

import com.p3achb0t.client.accounts.AccountManager
import com.p3achb0t.client.communication.ipc.Broker
import com.p3achb0t.client.communication.peer.PeerClient
import com.p3achb0t.client.scripts.loading.LoadScripts
import com.p3achb0t.client.tracker.FBDataBase
import com.p3achb0t.client.ux.BotManager
import com.p3achb0t.client.ux.BotTabBar

class GlobalStructs {

    companion object {

        var botManager: BotManager? = null

        val botTabBar = BotTabBar()
        // minimum [765,503]
        val width = 800
        val height = 600

        val scripts = LoadScripts()

        val communication = Broker()
        val peerClient = PeerClient()
        val accountManager = AccountManager()
        val db : FBDataBase = FBDataBase()
    }

}