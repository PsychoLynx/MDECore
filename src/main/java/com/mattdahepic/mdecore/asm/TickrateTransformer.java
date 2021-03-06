package com.mattdahepic.mdecore.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class TickrateTransformer implements IClassTransformer {
    @Override
    public byte[] transform (String name, String name2, byte[] bytes) {
        if (bytes == null) return null;

        try {
            if (name.equals("net.minecraft.server.MinecraftServer")) {
                System.out.println("Patching net.minecraft.server.MinecraftServer class for tickrate changing...");

                ClassNode classNode = new ClassNode();
                ClassReader classReader = new ClassReader(bytes);
                classReader.accept(classNode,0);

                for (MethodNode method : classNode.methods) {
                    if (method.name.equals("run") && method.desc.equals("()V")) {
                        InsnList list = new InsnList();
                        for (AbstractInsnNode node : method.instructions.toArray()) {
                            if (node instanceof LdcInsnNode) {
                                LdcInsnNode ldcNode = (LdcInsnNode)node;
                                if (ldcNode.cst instanceof Long && (Long)ldcNode.cst == 50L) {
                                    list.add(new FieldInsnNode(Opcodes.GETSTATIC,"com/mattdahepic/mdecore/helpers/TickrateHelper","MILISECONDS_PER_TICK","J"));
                                    continue;
                                }
                            }
                            list.add(node);
                        }
                        method.instructions.clear();
                        method.instructions.add(list);
                    }
                }
                ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                classNode.accept(writer);
                return writer.toByteArray();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
}
