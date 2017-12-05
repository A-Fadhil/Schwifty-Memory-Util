package smu.x64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import static smu.x64.Buffers.*;

import smu.bFlags;
import sun.misc.Unsafe;

public class MemUtil
{
	static
	{
		try{
			loadLib("smunatives\\");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void loadLib(String path)
	{
		try {
			boolean arch = System.getProperty("sun.arch.data.model").contains("64");
			String name = (System.getProperty("os.name").toLowerCase().contains("win") ? (arch ? "x64.dll" : "x32x64.dll") : null);

			// have to use a stream
			InputStream in = MemUtil.class.getResourceAsStream(name);
			// always write to different location
			File fileOut = new File(System.getProperty("java.io.tmpdir") + "/" + path + name);
			new File(fileOut.getParent()).mkdirs();
			OutputStream out = new FileOutputStream(fileOut);
			int read = 0;
			while((read = in.read()) != -1) out.write(read);
			in.close();
			out.flush();
			out.close();
			System.load(fileOut.toString());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/** pointer buffer **/
	/**
	 * sizeof()==24
	 **/
//	public static final class pBuffer
//	{
////		public static final long malloc(final long size)
////		{
////			return buffer(size * 8);
////		}
////
////		public static final long calloc(final long size)
////		{
////			return buffer(size * 8);
////		}
////
////		public static final void put(long address, long p)
////		{
////			buffer.put(address, p);
////		}
////
////		public static final void add(long address, long p)
////		{
////			buffer.append(address, p);
////		}
////
////		public static final void resize(long address, long size, int pBufferFlags)
////		{
////			MemUtil.resize(address, size * 8, pBufferFlags);
////		}
////
////		/**
////		 * offset 16
////		 **/
////		protected long self;
////
////		protected pBuffer()
////		{
////		}
////
////		public void put(long p)
////		{
////			buffer.put(self, p);
////		}
////		public void add(long p) { buffer.append(self, p); }
////		public long get()       { return buffer.getLong(self); }
////		public long get(long index)       { return buffer.getLong(index); }
////
////		public String toString()
////		{
////			return "0x" + Long.toHexString(self);
////		}
//	}
//
//	/**
//	 * int buffer
//	 **/
//	public static final class iBuffer
//	{
////		public static final native void put(int i);
//	}
//
//	/**
//	 * float buffer
//	 **/
//	public static final class fBuffer
//	{
////		public static final native void put(int i);
//	}
//
//	/**
//	 * long buffer
//	 **/
//	public static final class lBuffer
//	{
////		public static final native void put(int i);
//	}
//
//	/**
//	 * double buffer
//	 **/
//	public static final class dBuffer
//	{
////		public static final native void put(int i);
//	}

//	public static final native long buffer(long size);

//	public static final long resize(long buffer, long size, int flags)
//	{
//		if ((flags & bFlags.DELETE_REST) == 1)
//			return returnResizedCappedBuffer(buffer, size);
//		else
//		{
//			if ((flags & bFlags.RETURN_TYPE) == 0 && (flags & bFlags.MALLOC_REST) > 0)
//				return returnResizedMallocBufferAndEmpty(buffer, size);
//			else if ((flags & bFlags.RETURN_TYPE) == 0 && (flags & bFlags.CALLOC_REST) > 0)
//				return returnResizedCallocBufferAndEmpty(buffer, size);
//			else if ((flags & bFlags.RETURN_TYPE) > 0 && (flags & bFlags.MALLOC_REST) > 0)
//				return returnResizedMallocBufferAndType(buffer, size);
//			else if ((flags & bFlags.RETURN_TYPE) > 0 && (flags & bFlags.CALLOC_REST) > 0)
//				return returnResizedCallocBufferAndType(buffer, size);
//		}
//
////		realloc(buffer, size);
////		return buffer;
//
//		return returnResizedCappedBuffer(buffer, size);
//	}

//	public static final native long returnResizedCappedBuffer(long buffer, long size);
//
////	public static final native long returnResizedCappedBufferAndType(long buffer, int size);
////	public static final native long returnResizedCappedBufferAndEmpty(long buffer, int size);
//	public static final native long returnResizedMallocBufferAndType(long buffer, long size);
//
//	public static final native long returnResizedCallocBufferAndType(long buffer, long size);
//
//	public static final native long returnResizedMallocBufferAndEmpty(long buffer, long size);
//
//	public static final native long returnResizedCallocBufferAndEmpty(long buffer, long size);

	public static final native void realloc(long ptr, long size);

	public static final native long malloc(long size);

	public static final native long calloc(long size);

	public static final native void memcpy(long frm, long dst, long bts);

	public static final native void free(long ptr);

	public static final native long address(Object object);

	/**
	 * put inserts primitive at specific address
	 **/
	public static final native void put(long address, byte p);

	public static final native void put(long address, char p);

	public static final native void put(long address, short p);

	public static final native void put(long address, int p);

	public static final native void put(long address, float p);

	public static final native void put(long address, long p);

	public static final native void put(long address, double p);

	public static final void put(long address, String p)
	{
		int index = 0;
		put(address, (char) p.length());
		address = address + 2;
		for (int i = 0; i < p.length() * 2; i = i + 2) /* i know the incrementation can be simplified, ocd and preference got the better of me*/
			put(address + i, p.charAt(index++));
	}

//	public static final class buffer
//	{
//		/**
//		 * put adds to the buffer until it reaches the limit
//		 **/
//		public static final native void put(long self, byte p);
//
//		public static final native void put(long self, char p);
//
//		public static final native void put(long self, short p);
//
//		public static final native void put(long self, int p);
//
//		public static final native void put(long self, float p);
//
//		public static final native void put(long self, long p);
//
//		public static final native void put(long self, double p);
//
//		public static final void put(long self, String p)
//		{
//			int index = 0;
//			buffer.put(self, (char) p.length());
//			for (int i = 0; i < p.length(); i = i + 2) /* i know the incrementation can be simplified, ocd and preference got the better of me*/
//				buffer.put(self, p.charAt(index++));
//		}
//
//		/* this method, could be slowed, but it's useful for creating a list of objects. However it's important to remember that this uses up 8 bytes of memory */
//		public static final native void put(long self, Object jobject);
//
//
//		/**
//		 * append resizes the buffer at the limit
//		 **/
//		public static final native void append(long self, byte p);
//
//		public static final native void append(long self, char p);
//
//		public static final native void append(long self, short p);
//
//		public static final native void append(long self, int p);
//
//		public static final native void append(long self, float p);
//
//		public static final native void append(long self, long p);
//
//		public static final native void append(long self, double p);
//
//		public static final void append(long self, String p)
//		{
//			int index = 0;
//			buffer.append(self, (char) p.length());
//			for (int i = 0; i < p.length(); i = i + 2) /* i know the incrementation can be simplified, ocd and preference got the better of me*/
//				buffer.append(self, p.charAt(index++));
//		}
//
//		/* this method, could be slowed, but it's useful for creating a list of objects. However it's important to remember that this uses up 8 bytes of memory */
//		public static final native void append(long self, Object jobject);
//
//		public static final native byte getByte(long self);
//		public static final native char getChar(long self);
//		public static final native short getShort(long self);
//		public static final native int getInt(long self);
//		public static final native float getFloat(long self);
//		public static final native long getLong(long self);
//		public static final native double getDouble(long self);
//		public static final        String getString(long self)
//		{
//			String string = "";
//			int length = buffer.getChar(self);
//
//			for(int i = 0; i < length; i ++) string = string + buffer.getChar(i);
//			return string;
//		}
////		public static final        Object getObject(long self)
////		{
////			return return_j_object(buffer.getLong(self));
////		}
//
//		public static final native byte getByte(long self, long index);
//		public static final native char getChar(long self, long index);
//		public static final native short getShort(long self, long index);
//		public static final native int getInt(long self, long index);
//		public static final native float getFloat(long self, long index);
//		public static final native long getLong(long self, long index);
//		public static final native double getDouble(long self, long index);
//		public static final        String getString(long self, long index)
//		{
//			String string = "";
//			int length = buffer.getChar(self);
//
//			for(int i = 0; i < length; i ++) string = string + buffer.getChar(i);
//			return string;
//		}
////		public static final        Object getObject(long self, long index)
////		{
////			return return_j_object(buffer.getLong(self, index));
////		}
//	}

//	public static final native long limit(long self);
//	public static final native void rewind(long self);
	public static final native byte getByte(long address);
	public static final native char getChar(long address);
	public static final native short getShort(long address);
	public static final native int getInt(long address);
	public static final native float getFloat(long address);
	public static final native long getLong(long address);
	public static final native double getDouble(long address);
	public static final        String getString(long address)
	{
		String string = "";
		int length = getChar(address);
		address = address + 2;
		for(int i = 0; i < length; i ++)
		{
			string = string + getChar(address);
			address = address + 2;
		}

		return string;
	}
//	public static final        Object getObject(long self) { return return_j_object(getLong(self)); }

	public static native Object return_j_object(long address);
	public static final native long object_copy(Object object, long size);
//	public static final native long sizeof(Object object);

	public static final long store(byte p)
	{
		long address = malloc(1);

		put(address, p);

		return address;
	}

	public static final long store(char p)
	{
		long address = malloc(2);

		put(address, p);

		return address;
	}

	public static final long store(short p)
	{
		long address = malloc(2);

		put(address, p);

		return address;
	}

	public static final long store(int p)
	{
		long address = malloc(4);

		put(address, p);

		return address;
	}

	public static final long store(float p)
	{
		long address = malloc(4);

		put(address, p);

		return address;
	}

	public static final long store(long p)
	{
		long address = malloc(8);

		put(address, p);

		return address;
	}

	public static final long store(double p)
	{
		long address = malloc(8);

		put(address, p);

		return address;
	}

	public static final long store(String p)
	{
		long address = malloc(p.length() + 4);

		put(address, (char) p.length());
		put(address + 4, p);

		return address;
	}

	private static long mStaticPointBufferl = 0L;
	private static final Object mHelperArray[] = new Object[1];
	private static       long   m_shallow_mem_copy_pbuffer;
	protected static final pBuffer mpBuffer = new pBuffer();

//	private final static class container
//	{
//		//		private long cunt = 54;
//		private Object mHelperObject = new String();
//
//		protected container()
//		{
//		}
//	}

//	public static final container mContainer = new container();

	static
	{
		m_shallow_mem_copy_pbuffer = object_copy(mpBuffer, 24);
	}

	public static final pBuffer pBuffer(final long size)
	{
		long address = 123;//malloc(size);
		long jobject = malloc(24);

		memcpy(m_shallow_mem_copy_pbuffer, jobject, 24);
//		put(jobject + 16, address);

		return (pBuffer) return_j_object(jobject);
	}

	public static Unsafe mUnsafe;
	static {
		try {
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			mUnsafe = (Unsafe) f.get(null);//(Unsafe) f.get(null);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

//	static <T> T  shallowCopy(T obj) {
//		long size = sizeof(obj);
//		long start = toAddress(obj);
//		long address = mUnsafe.allocateMemory(size);
//		mUnsafe.copyMemory(start, address, size);
//		return (T) fromAddress(address);
//	}
//
//	static long toAddress(Object obj) {
//		Object[] array = new Object[] {obj};
//		long baseOffset = mUnsafe.arrayBaseOffset(Object[].class);
//		return normalize(mUnsafe.getInt(array, baseOffset));
//	}
//
//	static Object fromAddress(long address) {
//		Object[] array = new Object[] {null};
//		long baseOffset = mUnsafe.arrayBaseOffset(Object[].class);
//		mUnsafe.putLong(array, baseOffset, address);
//		return array[0];
//	}

	public static long sizeof(Object o)
	{
		HashSet<Field> fields = new HashSet<Field>();
		Class c = o.getClass();
		while (c != Object.class)
		{
			for (Field f : c.getDeclaredFields())
			{
				if ((f.getModifiers() & Modifier.STATIC) == 0)
				{
					fields.add(f);
				}
			}
			c = c.getSuperclass();
		}

		// get offset
		long maxSize = 0;
		for (Field f : fields)
		{
			long offset = mUnsafe.objectFieldOffset(f);
			if (offset > maxSize)
			{
				maxSize = offset;
			}
		}

		return ((maxSize / 8) + 1) * 8;   // padding
	}

	public static long normalize(long value)
	{
		if (value >= 0) return value;
		return (~0L >>> 32) & value;
	}

	public static final void delete()
	{
//		System.out.println(mpBuffer);
	}
}