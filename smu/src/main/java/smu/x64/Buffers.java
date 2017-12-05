package smu.x64;

/**
 * Created by Aboody on 07/10/2017.
 */
public class Buffers
{
    public static final class pBuffer
	{
//		public static final long malloc(final long size)
//		{
//			return buffer(size * 8);
//		}
//
//		public static final long calloc(final long size)
//		{
//			return buffer(size * 8);
//		}
//
//		public static final void put(long address, long p)
//		{
//			buffer.put(address, p);
//		}
//
//		public static final void add(long address, long p)
//		{
//			buffer.append(address, p);
//		}
//
//		public static final void resize(long address, long size, int pBufferFlags)
//		{
//			MemUtil.resize(address, size * 8, pBufferFlags);
//		}

		/**
		 * offset 16
		 **/
		protected long self;

		protected pBuffer()
		{
		}

//		public void put(long p)
//		{
//			buffer.put(self, p);
//		}
//		public void add(long p) { buffer.append(self, p); }
//		public long get()       { return buffer.getLong(self); }
//		public long get(long index)       { return buffer.getLong(index); }

		public String toString()
		{
			return "0x" + Long.toHexString(self);
		}
	}
}