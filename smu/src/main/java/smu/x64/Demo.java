package smu.x64;

//import java.lang.instrument.Instrumentation;

//import org.openjdk.jol.info.ClassLayout;
//import smu.x64.MemUtil.pBuffer;

public class Demo
{
	public static void main(String[] args)
	{
//		System.out.println(org.openjdk.jol.info.ClassLayout.parseInstance(MemUtil.mContainer).toPrintable());

//		System.out.println(MemUtil.pBuffer(15));

		long mal = MemUtil.malloc(120);
		long mal2 = MemUtil.malloc(120);
//		for(int i = 0; i < 120; i++) MemUtil.put(mal + i, (byte)i);
//		MemUtil.memcpy(mal, mal2, 120);
//		for(int i = 0; i < 120; i++) System.out.println(MemUtil.getByte(mal2 + i));
		Buffers.pBuffer buffer = new Buffers.pBuffer();
		Buffers.pBuffer buffer2 = new Buffers.pBuffer();


		long now = System.nanoTime();
		/*
		unsafe

		 */

		/*
		malloc
		0.315564544
		0.311958453
		0.252043719
		 */

		for(int i = 0; i < 1000000; i++)
		{
			MemUtil.put(MemUtil.malloc(50), 345L);
		}

		System.out.println((System.nanoTime() - now) / 1000000000.0);
		//unsafe = 0.078172314

		now = System.nanoTime();

		for(int i = 0; i < 1000000; i++)
		{
			MemUtil.mUnsafe.putLong(MemUtil.mUnsafe.allocateMemory(504), 435345L);
		}

		System.out.println((System.nanoTime() - now) / 1000000000.0);
		now = System.nanoTime();

		for(int i = 0; i < 1000000; i++)
		{
			MemUtil.mUnsafe.allocateMemory(504);
		}

		System.out.println((System.nanoTime() - now) / 1000000000.0);

		System.out.println(MemUtil.sizeof(buffer));

//		MemUtil.shallowCopy(buffer);

//		MemUtil.put(mal, "434ttgfgfgh");
//		Buffers.pBuffer buffer = MemUtil.pBuffer(4);
//		MemUtil.put(mal, object);

//		System.out.println(MemUtil.getString(mal) + ";");

		MemUtil.delete();
	}
}