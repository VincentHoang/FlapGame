package com.mygdx.game.world

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
class RandomObstacles(val world: World) {

    val staticObstacles: MutableList<Body> = mutableListOf()
    var lastX: Float = 0f
    val maxWidth = 10f

    fun initialGenerate() {
        staticObstacles.add(createStaticObstacle(30f, 15f, 3f, 3f))
        staticObstacles.add(createStaticObstacle(50f, 20f, 4f, 4f))
        staticObstacles.add(createStaticObstacle(80f, 7f, 2f, 5f))
    }

    fun generate(positionX: Float, worldHeight: Float, worldWidth: Float) { //screen width
        val x = positionX + worldWidth

        if (x - maxWidth > lastX + maxWidth) {
            val y = MathUtils.random(5f, worldHeight - 5f)
            val width = MathUtils.random(3f, maxWidth)
            val height = MathUtils.random(3f, maxWidth)
            staticObstacles.add(
                createStaticObstacle(
                    x,
                    y,
                    width,
                    height
                )
            )

            lastX = x

            println("generating: ($x, $y)")
        }
    }
    
    fun cleanup(positionX: Float, worldWidth: Float) {
        if (staticObstacles[0].position.x < positionX - worldWidth) { //TODO 
            val removeAt = staticObstacles.removeAt(0)
            world.destroyBody(removeAt)
        }
    }

    private fun createStaticObstacle(x: Float, y: Float, width: Float, height: Float): Body {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.StaticBody
        bodyDef.position.set(x, y)

        val body = world.createBody(bodyDef)
        val shape = PolygonShape()
        shape.setAsBox(width/2, height/2)

        body.createFixture(shape, 0.0f)
        body.userData = Vector2(width, height) //TODO too hacky
        shape.dispose()
        return body
    }
}