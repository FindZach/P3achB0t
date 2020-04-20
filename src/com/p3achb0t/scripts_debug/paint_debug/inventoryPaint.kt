package com.p3achb0t.scripts_debug.paint_debug

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.tabs.Tabs
import java.awt.Color
import java.awt.Graphics

fun inventoryPaint(g: Graphics, ctx: Context) {
    try {

        if(ctx.tabs.getOpenTab() == Tabs.Tab_Types.Inventory) {
            val items = ctx.inventory.getAll()
            // Look at inventory
            if (!items.isNullOrEmpty()) {
                if (items.size > 0) {
                    items.forEach {
                        g.color = Color.YELLOW
                        g.drawString("${it.id}", it.getBasePoint().x, it.getBasePoint().y - 1)
                        g.color = Color.yellow
                        g.drawString("${it.stackSize}", it.getBasePoint().x, it.getBasePoint().y + 20)

                        g.color = Color.RED
                        g.drawRect(it.area.x, it.area.y, it.area.width, it.area.height)
                    }
                }
            }
        }
    } catch (e: Exception) {
        println("Error: Inventory " + e.message)
    }
}
