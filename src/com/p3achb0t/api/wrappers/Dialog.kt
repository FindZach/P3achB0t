package com.p3achb0t.api.wrappers

import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetID.Companion.DIALOG_PLAYER_GROUP_ID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import kotlinx.coroutines.delay
import kotlin.random.Random

class Dialog(val client: com.p3achb0t._runestar_interfaces.Client) {
    companion object {
        private const val PARENT = WidgetID.DIALOG_NPC_GROUP_ID
        private const val CONTINUE = WidgetID.DialogNPC.CONTINUE
        private const val PARENT_BACKUP = DIALOG_PLAYER_GROUP_ID
        private const val CONTINUE_BACKUP = WidgetID.DialogNPC.CONTINUE
        private const val PARENT_DIALOG_OPTIONS = WidgetID.DIALOG_OPTION_GROUP_ID
        private const val PARENT_BACKUP_2 = 229
        private const val CONTINUE_BACKUP_2 = 2
        private const val PARENT_BACKUP_3 = 193
        private const val CONTINUE_BACKUP_3 = 0
        private const val PARENT_BACKUP_4 = 11
        private const val CONTINUE_BACKUP_4 = 4
    }


    fun isDialogUp(): Boolean {
        return getDialogContinue().widget != null
    }

    fun getDialogContinue(): WidgetItem {
        var dialog = WidgetItem(Widgets.find(client, PARENT, CONTINUE), client = client)
        if (dialog.widget == null || (dialog.widget != null && !dialog.containsText("continue"))) {
            dialog = WidgetItem(Widgets.find(client, PARENT_BACKUP, CONTINUE_BACKUP), client = client)
            if (dialog.widget == null || (dialog.widget != null && !dialog.containsText("continue"))) {
                dialog = WidgetItem(Widgets.find(client, PARENT_BACKUP_2, CONTINUE_BACKUP_2), client = client)
                if (dialog.widget == null || (dialog.widget != null && !dialog.containsText("continue"))) {
                    dialog = WidgetItem(Widgets.find(client, PARENT_BACKUP_3, CONTINUE_BACKUP_3), client = client)
                    if (dialog.widget == null || (dialog.widget != null && !dialog.containsText("continue"))) {
                        dialog = WidgetItem(Widgets.find(client, PARENT_BACKUP_4, CONTINUE_BACKUP_4), client = client)
                    }
                }
            }
        }

        return dialog
    }

    suspend fun continueDialog(sleep: Boolean = true) {
        while (getDialogContinue().containsText("continue")) {
            doConversation(sleep)
        }
    }

    private suspend fun doConversation(sleep: Boolean) {
        val dialog = getDialogContinue()
        if (dialog.containsText("continue", false)) {
            dialog.click()
            delay(Random.nextLong(100, 200))

        } else if (dialog.containsText("continue")) {
            //NEed to find children
            dialog.widget?.getChildren()?.iterator()?.forEach {
                if (WidgetItem(it, client = client).containsText("continue")) {
                    WidgetItem(it, client = client).click()
                    delay(Random.nextLong(100, 200))
                }
            }
        }
        //TODO - add a smart sleep based on the number of words in the continue dialog
        if (sleep)//&& getDialogContinue().containsText("continue"))
            delay(Random.nextLong(1250, 3650))
    }

    suspend fun selectionOption(action: String) {
        val dialog = WidgetItem(Widgets.find(client, PARENT_DIALOG_OPTIONS, 1), client = client)
        // Options are in children but not index zero
        dialog.widget?.getChildren()?.iterator()?.forEach {
            if (it.getText().contains(action)) {
                WidgetItem(it, client = client).click()
                delay(Random.nextLong(1500, 2500))
            }
        }
    }

    suspend fun selectRandomOption() {
        val dialog = WidgetItem(Widgets.find(client, PARENT_DIALOG_OPTIONS, 1), client = client)
        val childrenSize = dialog.widget?.getChildren()?.size ?: 0
        if (childrenSize == 0) return
        val randOptionIndex = Random.nextInt(1, childrenSize)
        WidgetItem(dialog.widget?.getChildren()?.get(randOptionIndex), client = client).click()
    }
}