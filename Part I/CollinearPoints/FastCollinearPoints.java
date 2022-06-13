import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private final LineSegment[] Segs;

    public FastCollinearPoints(Point[] points)
    {
        if(!isValid(points))
        {
            throw new IllegalArgumentException("Invalid Points!");
        }

        Arrays.sort(points);
        List<LineSegment> list = new ArrayList<>();
        int len = points.length;

        for (int i = 0; i < len; i++)
        {
            // Point p is base point
            Point basePoint = points[i];
            // get sorted array for the rest point
            Point[] resPoints = getRestPoints(points, i);

            for (int j = 0; j < len-1; )
            {
                List<Point> currPoints = new ArrayList<>();
                double slope = basePoint.slopeTo(resPoints[j]);
                while(j < len-1 && slope == basePoint.slopeTo(resPoints[j]))
                {
                    currPoints.add(resPoints[j]);
                    j++;
                }
                int size = currPoints.size();
                if (size >= 3 && basePoint.compareTo(currPoints.get(size - 1)) < 0)
                {
                    Point lastPoint = currPoints.get(size - 1);
                    list.add(new LineSegment(basePoint, lastPoint));
                }
            }
        }
        Segs = list.toArray(new LineSegment[0]);
    }

    private Point[] getRestPoints(Point[] points, int index)
    {
        int len = points.length;
        Point[] res = new Point[len-1];
        int curr = 0;
        for(int i = 0; i < len; i++)
        {
            if(i != index)
            {
                res[curr++] = points[i];
            }
        }
        Arrays.sort(res, points[index].slopeOrder());
        return res;
    }


    public int numberOfSegments()
    {
        return Segs.length;
    }

    public LineSegment[] segments()
    {
        return Segs.clone();
    }

    private boolean isValid(Point[] points)
    {
        if(points == null)
            return false;
        for(Point p : points)
        {
            if(p == null)
                return false;
        }
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++)
        {
            if (points[i].compareTo(points[i-1]) == 0)
            {
                return false;
            }

        }
        return true;
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
