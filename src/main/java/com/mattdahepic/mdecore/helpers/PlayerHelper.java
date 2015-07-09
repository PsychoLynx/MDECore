package com.mattdahepic.mdecore.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import java.util.List;

public class PlayerHelper {
    public BlockPos getPlayerPosAsBlockPos (EntityPlayer player) {
        return new BlockPos(player.posX,player.posY,player.posZ);
    }
    public static int[] getPlayerPosAsIntegerArray (EntityPlayer player) {
        int posX = MathHelper.floor_double(player.posX);
        int posY = MathHelper.floor_double(player.posY);
        int posZ = MathHelper.floor_double(player.posZ);
        return new int[]{posX,posY,posZ};
    }
    public static int getDistanceFromXZ (EntityPlayer player, int pointX, int pointZ) {
        int[] playerPos = getPlayerPosAsIntegerArray(player);
        int playerX = playerPos[0];
        int playerZ = playerPos[2];
        int xLength = pointX-playerX;
        int zLength = pointZ-playerZ;
        return Math.abs(MathHelper.floor_double(findHyp(xLength,zLength)));
    }
    public static int getDistanceFrom (EntityPlayer player, int pointX, int pointY, int pointZ) {
        int horizontalLength = getDistanceFromXZ(player,pointX,pointZ);
        int verticalLength = pointY-getPlayerPosAsIntegerArray(player)[1];
        return Math.abs(MathHelper.floor_double(findHyp(horizontalLength,verticalLength)));
    }
    private static double findHyp (double side1, double side2) {
        //c^2=a^2+b^2 aka pythagorean theorem
        return Math.sqrt((side1 * side1)+(side2*side2));
    }
}
