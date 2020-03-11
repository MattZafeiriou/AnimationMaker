package MattZafeiriou.Animations;

public class Smoother
{

	private int frames = 0;
	private float speed = 0, current = 0, first, end;

	public Smoother( float start, float end, int frames )
	{
		this.end = end;
		this.frames = Math.abs( frames );
		current = start;

		if( frames != 0 )
			speed = ( end - start ) / frames;
		else
			speed = 0;

		if( start > end )
			speed *= - 1;
	}

	public float getValue()
	{
		if( frames == 1 )
			first = current;
		if( frames == 0 )
			return first;

		frames--;
		current += speed;
		return current;
	}

	public float getEnd()
	{
		return end;
	}

}
