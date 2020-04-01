package com.p3achb0t.scripts.debug.paint

import com.p3achb0t.api.DebugScript
import com.p3achb0t.api.ScriptManifest
import java.awt.Color
import java.awt.Graphics

@ScriptManifest("Debug","WidgetBlocking Helper","Bot Team", "0.1")
class DebugWidgetBlocking : DebugScript() {
    override fun draw(g: Graphics) {
        if (ctx.client.getGameState() == 30) {
            g.color = Color.red
            /*drawRect(
                    g,
                    Calculations.chatBoxDimensions
            )
            drawRect(
                    g,
                    Calculations.inventoryBarBottomDimensions
            )
            drawRect(
                    g,
                    Calculations.inventoryDimensions
            )
            drawRect(
                    g,
                    Calculations.inventoryBarTopDimensions
            )
            drawRect(
                    g,
                    Calculations.miniMapDimensio
        */
        }
    }

    override fun start() {

    }

    override fun stop() {

    }

}
