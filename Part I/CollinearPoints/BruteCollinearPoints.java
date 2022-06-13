import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private Point[] copyOfPoints;
    private List<LineSegment> lines = new ArrayList<>();


    public BruteCollinearPoints(Point[] points)
    {
        if(!isValid(points)){
            throw new IllegalArgumentException("Invalid Input");
        }
        int len  = points.length;
        copyOfPoints = new Point[len];
        for (int i = 0; i < points.length; i++)
        {
            copyOfPoints[i] = points[i];
        }
        Arrays.sort(copyOfPoints);

        for (int i = 0; i < len - 3; i++)
        {
            Point p1 = copyOfPoints[i];
            for (int j = i + 1; j < len - 2; j++)
            {
                Point p2 = copyOfPoints[j];
                double slope = p1.slopeTo(p2);
                for (int k = j + 1; k < len - 1; k++)
                {
                    Point p3 = copyOfPoints[k];
                    if (p1.slopeTo(p3) == slope)
                    {
                        for (int l = k + 1; l < len; l++)
                        {
                            Point p4 = copyOfPoints[l];
                            if (p1.slopeTo(p4) == slope)
                            {
                                lines.add(new LineSegment(p1, p4));
                                break;
                            }
                        }
                    }
                }
            }
        }

    }
    public int numberOfSegments()
    {
        return lines.size();
    }
    public LineSegment[] segments()
    {
        if(numberOfSegments() > 0)
        {
            LineSegment[] arr = new LineSegment[lines.size()];
            lines.toArray(arr);
            return arr;
        }
        else
            return new LineSegment[0];

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
        for (int i = 0; i < points.length-1; i++)
        {
            Point p1 = points[i];
            for (int j = i + 1; j < points.length; j++)
            {
                Point p2 = points[j];
                if(p1.compareTo(p2) == 0)
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
