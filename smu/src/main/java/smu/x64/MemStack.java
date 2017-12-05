package smu.x64;

import static smu.x64.MemUtil.*;

public class MemStack
{
    protected static long stack[];
    protected static int  index;
    protected static boolean dynamic;

    public static void DynamicStack(boolean dynamic)
    {
        DynamicStack(10, dynamic);
    }

    public static void DynamicStack(int initialSize)
    {
        DynamicStack(initialSize, false);
    }

    public static void DynamicStack(int initialStackSize, boolean dynamic)
    {
        stack = new long[initialStackSize];
        MemStack.dynamic = dynamic;
    }

    public static void push(long i)
    {
        if(index + 1 >= stack.length)
        {
            if(!dynamic) throw new StackOverflowError("MemStack is full.");
            long nstack[] = new long[(int) (stack.length * 1.1)];
            System.arraycopy(stack, 0, nstack, 0, stack.length);

            stack = nstack;
        } else if(index < stack.length / 1.1  && dynamic)
        {
            long nstack[] = new long[(int) (stack.length / 1.1)];
            System.arraycopy(stack, 0, nstack, 0, nstack.length);

            stack = nstack;
        }
        stack[index ++] = i;
    }

//    public static pBuffer pBuffer(long size)
//    {
////        long buffer = pBuffer.malloc(size);
////
////        mpBuffer.self = buffer;
//
//        return mpBuffer;
//    }

    public static long pop()
    {
        return stack[-- index];
    }
    public static long peek() { return stack[index - 1]; }
}