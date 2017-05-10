package org.badgrades

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import ktx.app.KotlinApplication
import ktx.app.use
import ktx.collections.GdxArray
import ktx.collections.gdxMapOf
import ktx.collections.toGdxArray
import ktx.collections.toGdxList
import java.awt.Point

class MyGdxGame : KotlinApplication(fixedTimeStep = 1f / 60f, maxDeltaTime = 1 / 15f) {

    val unitScale = 1f / 32f

    lateinit var batch: SpriteBatch
    lateinit var tiledMap: TiledMap
    lateinit var tiledRenderer: OrthogonalTiledMapRenderer
    lateinit var camera: OrthographicCamera

    lateinit var preSpriteTiles: IntArray
    lateinit var postSpriteTiles: IntArray

    // Character
    val animationSpeed = 0.5f
    val animationMap = gdxMapOf<Direction, Animation<TextureRegion>>()
    var direction = Direction.RIGHT
    var stateTime = 0f
    var location = Point(250, 250)

    lateinit var characterAtlas: TextureAtlas

    override fun create() {
        batch = SpriteBatch()
        tiledMap = TmxMapLoader().load("maps/test-lush.tmx")
        tiledRenderer = OrthogonalTiledMapRenderer(tiledMap)
        characterAtlas = TextureAtlas(Gdx.files.internal("sprites/kaguya.atlas"))
        camera = OrthographicCamera()
        camera.setToOrtho(false, Gdx.graphics.width.toFloat() + 200f, Gdx.graphics.height.toFloat() + 120f)

        tiledMap.layers.forEachIndexed { index, mapLayer ->
            if (mapLayer.name.equals("player", ignoreCase = true)) {
                preSpriteTiles = (0..index).toList().toIntArray()

                try {
                    postSpriteTiles = (index + 1..tiledMap.layers.count - 1).toList().toIntArray()
                } catch (e: IndexOutOfBoundsException) {
                    postSpriteTiles = (index..tiledMap.layers.count - 1).toList().toIntArray()
                }
            }
        }

        val upAnimation = Animation<TextureRegion>(animationSpeed, characterAtlas.findRegions("kaguya-up"), Animation.PlayMode.LOOP)
        val downAnimation = Animation<TextureRegion>(animationSpeed, characterAtlas.findRegions("kaguya-down"), Animation.PlayMode.LOOP)
        val leftAnimation = Animation<TextureRegion>(animationSpeed, characterAtlas.findRegions("kaguya-left"), Animation.PlayMode.LOOP)
        val rightAnimation = Animation<TextureRegion>(animationSpeed, characterAtlas.findRegions("kaguya-right"), Animation.PlayMode.LOOP)

        animationMap.put(Direction.UP, upAnimation)
        animationMap.put(Direction.DOWN, downAnimation)
        animationMap.put(Direction.LEFT, leftAnimation)
        animationMap.put(Direction.RIGHT, rightAnimation)
    }

    override fun render(delta: Float) {
        val currentFrame = animationMap.get(direction).getKeyFrame(stateTime, true)

        camera.update()
        tiledRenderer.setView(camera)
        stateTime += Gdx.graphics.deltaTime

        batch.use {
            tiledRenderer.render(preSpriteTiles)
            it.draw(currentFrame, location.x.toFloat(), location.y.toFloat())
            tiledRenderer.render(postSpriteTiles)
        }
    }

    override fun dispose() {
        batch.dispose()
        tiledRenderer.dispose()
        tiledMap.dispose()
    }
}

enum class Direction(val offset: Point) {
    UP(Point(0, 1)),
    DOWN(Point(0, -1)),
    LEFT(Point(-1, 0)),
    RIGHT(Point(1, 0));
}