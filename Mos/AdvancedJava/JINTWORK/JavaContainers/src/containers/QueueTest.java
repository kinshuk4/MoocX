package containers;

import java.util.*;

class QueueTest 
{
	public static void main(String[] args) 
	{
		Queue queue=new PriorityQueue();
		queue.offer(new CompType(1,2));
		queue.offer(new CompType(3,4));
		queue.offer(new CompType(7,2));
		queue.offer(new CompType(0,7));
		System.out.println(queue);
		Iterator i=queue.iterator();
		while (i.hasNext())
		{
			System.out.println(i.next());
		}
		Iterator ii=queue.iterator();
		while (ii.hasNext())
		{
			System.out.println(queue.remove());
		}
		
	}
}
