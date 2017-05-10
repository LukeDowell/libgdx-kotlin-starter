package org.badgrades.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.badgrades.MyGdxGame

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.height = 900
        config.width = 1600
        LwjglApplication(MyGdxGame(), config)
    }
}
