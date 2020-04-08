package com.p3achb0t.scripts_debug.paint_debug

import com.p3achb0t._runestar_interfaces.EvictingDualNodeHashTable
import com.p3achb0t._runestar_interfaces.LocType
import com.p3achb0t._runestar_interfaces.Model
import com.p3achb0t._runestar_interfaces.Wall
import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.GameObject
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.ObjectPositionInfo
import com.p3achb0t.api.wrappers.utils.getConvexHullFromModel
import com.p3achb0t.api.wrappers.utils.getTrianglesFromModel
import java.awt.Color
import java.awt.Graphics
import java.awt.Point
import java.awt.Polygon

fun gameObjectPaint(g: Graphics, ctx: Context) {
    if (true) {
        val sceneData = ctx.client.getObjType_cachedModels()
        val region = ctx.client.getScene()
        val localPlayer = ctx.players.getLocal()
        var planeInt = 0
        region.getTiles().iterator().forEach { plane ->
//            if (planeInt == ctx.client.getPlane()) {
            if (true) {
                plane.iterator().forEach { row ->

                    row.iterator().forEach { tile ->
                        if (tile != null) {

                            //Floor decoration
                            val floorDecoration = tile.getFloorDecoration()

                            if (floorDecoration != null) {
                                val go_fd = GameObject(floorDecoration = floorDecoration, ctx = ctx)
                                val globalPos_fd = go_fd.getGlobalLocation()
                                if (localPlayer.distanceTo(globalPos_fd) < 5
                                        && planeInt == ctx.players.getLocal().player.getPlane()) {

                                    val floorModel = go_fd.model!!
                                    val positionInfo =
                                            ObjectPositionInfo(
                                                    floorDecoration.getX(),
                                                    floorDecoration.getY(),
                                                    0
                                            )

                                    val modelTriangles =
                                            getTrianglesFromModel(
                                                    positionInfo,
                                                    floorModel,
                                                    ctx

                                            )
                                    g.color = Color.pink
                                    if (go_fd.isMouseOverObj()) {
                                    modelTriangles.forEach {
                                        g.drawPolygon(it)
                                    }
                                    val hull = getConvexHullFromModel(
                                            positionInfo,
                                            floorModel,
                                            ctx

                                    )
                                    g.color = Color.darkGray
                                    g.drawPolygon(hull)

                                    g.color = Color.YELLOW
                                    val point2 =
                                            Calculations.worldToScreen(
                                                    floorDecoration.getX(),
                                                    floorDecoration.getY(),
                                                    floorDecoration.getEntity().getHeight(),
                                                    ctx
                                            )
                                    val objectComposite =
                                            getObjectComposite(sceneData, go_fd.id)
                                        g.drawString(
                                                "(ID:${go_fd.id})(${globalPos_fd.x},${globalPos_fd.y})",
                                                point2.x,
                                                point2.y)
                                    }
                                }
                            }

                            //Scene entitys

                            if (tile.getScenery().isNotEmpty()) {
                                var count = 0
                                tile.getScenery().iterator().forEach {
                                    if (it != null && it.getTag() > 0) {

                                        val testID = 23145
                                        val id2 = it.getTag().shr(17).and(0x7fff).toInt()
                                        if (id2 == testID) {
                                            println("log found")
                                        }
                                        count += 1
                                        // Print out the polygons for the models
                                        if (false) {
                                            val tilePolygon =
                                                    Calculations.getCanvasTileAreaPoly(ctx,
                                                            it.getCenterX(),
                                                            it.getCenterY()
                                                    )
                                            g.color = Color.ORANGE
                                            g.drawPolygon(tilePolygon)
                                        }

                                        val go = GameObject(it, ctx = ctx)
                                        val globalPos = go.getGlobalLocation()

                                        val point =
                                                Calculations.worldToScreen(
                                                        it.getCenterX(),
                                                        it.getCenterY(),
                                                        planeInt,
                                                        ctx

                                                )


                                        //Printing out the model and the hull
                                        if (localPlayer.distanceTo(globalPos) < 4
                                                && planeInt == ctx.players.getLocal().player.getPlane()) {
                                            var model = it.getEntity()
                                            if (!(model is Model)) {
                                                model = model.getModel()
                                            }
                                            if (model is Model) {
                                                val positionInfo =
                                                        ObjectPositionInfo(
                                                                it.getCenterX(),
                                                                it.getCenterY(),
                                                                it.getOrientation()
                                                        )

                                                val modelTriangles =
                                                        getTrianglesFromModel(
                                                                positionInfo,
                                                                model,
                                                                ctx

                                                        )
                                                val hull = getConvexHullFromModel(
                                                        positionInfo,
                                                        model,
                                                        ctx

                                                )
                                                g.color = Color.RED
                                                val mousePoint = Point(ctx?.mouse?.getX() ?: -1, ctx?.mouse?.getY()
                                                        ?: -1)
                                                if (hull.contains(mousePoint)) {
                                                    modelTriangles.forEach {
                                                        g.drawPolygon(it)
                                                    }
                                                }
                                                if (hull.contains(mousePoint)) {
                                                    g.color = Color.CYAN
                                                    g.drawPolygon(hull)
                                                }

                                                g.color = Color.GREEN
                                                val id = it.getTag().shr(17).and(0x7fff).toInt()
                                                val rawID = it.getTag().shr(14).and(0x7fff)
                                                //                                            println("${it.getWidgetID()},$rawID,$widgetID,${it.getRenderable().getWidgetID()}")
                                                val objectComposite =
                                                        getObjectComposite(sceneData, id)
                                                val point2 =
                                                        Calculations.worldToScreen(
                                                                it.getCenterX(),
                                                                it.getCenterY(),
                                                                it.getEntity().getHeight(),
                                                                ctx

                                                        )
                                                val localPos = go.getLocalLocation()

                                                //Add offset on the Y so Things on the same tile do not stack
                                                val offsetY = (count - 1) * 30
                                                //For now only filter objects near m
                                                if (hull.contains(mousePoint)) {
                                                    g.drawString(
                                                            "(ID:${go.id})(${globalPos.x},${globalPos.y}) l(${localPos.x},${localPos.y})",
                                                            point2.x,
                                                            point2.y + offsetY
                                                    )
                                                }
                                            }
                                            //Filter only near me: if(abs(globalPos.x - localPlayer.getGlobalLocation().x) <5 && abs(globalPos.y - localPlayer.getGlobalLocation().y) < 5)
                                        }
                                    }
                                }
                            }


                            val globalPos =
                                    Tile(
                                            tile.getX() + ctx.client.getBaseX(),
                                            tile.getY() + ctx.client.getBaseY(),
                                            ctx = ctx
                                    )

                            //print out wall decorations
                            if (tile.getWallDecoration() != null && localPlayer.distanceTo(globalPos) < 5) {
                                val wallDecoration = tile.getWallDecoration()
                                val id = wallDecoration.getTag().shr(17).and(0x7fff).toInt()
                                val objectComposite =
                                        getObjectComposite(sceneData, id)
                                val positionInfo =
                                        ObjectPositionInfo(
                                                wallDecoration.getX(),
                                                wallDecoration.getY(),
                                                wallDecoration.getOrientation()
                                        )
                                if(wallDecoration != null
                                        && wallDecoration.getEntity1() != null) {
                                    if(wallDecoration.getEntity1() is Model) {
                                        val model = wallDecoration.getEntity1() as Model
                                        val modelTriangles =
                                                getTrianglesFromModel(
                                                        positionInfo,
                                                        model,
                                                        ctx

                                                )
                                        g.color = Color.PINK
                                        modelTriangles.forEach {
                                            g.drawPolygon(it)
                                        }
                                    }
                                }
                            }


//                        println("Tile: ${tile.getCenterX()},${tile.getCenterY()} locGlob: ${localPlayer.getGlobalLocation()} localReg: ${localPlayer.getRegionalLocation()}")
                            // Display the wall object
                            if (tile.getWall() != null && localPlayer.distanceTo(globalPos) < 5) {
                                val wall = tile.getWall()
                                val wo = GameObject(wallObject = wall, ctx = ctx)

                                g.color = Color.CYAN
                                val id = wall.getTag().shr(17).and(0x7fff).toInt()
                                //                                            println("${it.getWidgetID()},$rawID,$widgetID,${it.getRenderable().getWidgetID()}")
                                val objectComposite =
                                        getObjectComposite(sceneData, id)

                                if (wall.getEntity1() != null) {
                                    val model = wall.getEntity1().getModel()
                                    if (model != null)
                                        paintWallModel(wall, model, ctx, g)
                                }

                                if (wall.getEntity2() != null) {
                                    val model2 = wall.getEntity2().getModel()
                                    if (model2 != null)
                                        paintWallModel(wall, model2, ctx, g)
                                }

                                val point2 =
                                        Calculations.worldToScreen(
                                                wall.getX(),
                                                wall.getY(),
                                                wall.getEntity1().getHeight(),
                                                ctx
                                        )
                                if(wo.isMouseOverObj()) {
                                    g.drawString(
                                            "(${wo.name})(ID:${wo.id})(${globalPos.x},${globalPos.y})",
                                            point2.x,
                                            point2.y
                                    )
                                }
                            }
                        }
                    }
                }
            }
            planeInt += 1
        }
    }
}

private fun paintWallModel(wall: Wall, model: Model, ctx: Context, g: Graphics) {
    val positionInfo =
            ObjectPositionInfo(
                    wall.getX(),
                    wall.getY(),
                    wall.getOrientationA()
            )

    val modelTriangles =
            getTrianglesFromModel(
                    positionInfo,
                    model,
                    ctx

            )
    g.color = Color.RED
    val mousePoint = Point(ctx?.mouse?.getX() ?: -1, ctx?.mouse?.getY() ?: -1)
    val hull = getConvexHullFromModel(
            positionInfo,
            model,
            ctx

    )
    if(hull.contains(mousePoint)) {
        modelTriangles.forEach {
            g.drawPolygon(it)
        }
    }

    if(hull.contains(mousePoint)) {
        g.color = Color.CYAN
        g.drawPolygon(hull)
    }
}

fun getObjectComposite(
        objectCache: EvictingDualNodeHashTable,
        gameObjectId: Int
): LocType? {
    var desiredGameObject1: LocType? = null
    objectCache.getHashTable().getBuckets().iterator().forEach { bucketItem ->
        if (bucketItem != null) {

            var objectComposite = bucketItem.getNext()
            while (objectComposite != null
                    && objectComposite is LocType
                    && objectComposite != bucketItem
            ) {
                if (objectComposite.getKey() > 0
                        && objectComposite.getKey().toInt() == gameObjectId
                ) {
                    desiredGameObject1 = objectComposite
                    break
                }
                objectComposite = objectComposite.getNext()
            }
        }
    }
    return desiredGameObject1
}