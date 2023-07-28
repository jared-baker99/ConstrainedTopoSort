// Jared Baker
// NID: ja907583
// Assignment 5: Topological Sort
// In this assignment we are given a graph and want to see if a valid topological sort is available
// where X vertex comes before Y vertex

import java.io.*;
import java.util.*;

public class ConstrainedTopoSort
{
  ArrayList<LinkedList<Integer>> adjList = new ArrayList<>();
  // Opens the file and reads the graph into a adjacency List
  public ConstrainedTopoSort(String filename) throws IOException
  {
    Scanner in = new Scanner(new File(filename));
    int numVertices = in.nextInt();
    // traverses the vertices
    for (int i = 0; i < numVertices; i++)
    {
      adjList.add(new LinkedList<Integer>());
      // the number of vertices that follow the given vertex
      int numFollow = in.nextInt();
      // assigns what each vertex is pointing to ROW = vertex, COLLUMN = prerequisite of vertex
      for(int j = 0; j < numFollow; j++)
      {
        adjList.get(i).add(in.nextInt() - 1);
      }
    }
  }

  // Determins if the graph has a topological sort where X precedes Y
  // The code in this function is similar to the toposort.java in webcourses from Szumlanski
  // The difference between them is this one would have the X node take priority and the Y node
  // have the least priority being put in as late as possible
  public boolean hasConstrainedTopoSort(int x, int y)
  {
    int incoming [] = new int[adjList.size()];
    // creates an array for the nodes that have edges going into them
    for (int i = 0; i < adjList.size(); i++)
    {
      // loops through the adjacency list
      for (int j = 0; j < adjList.get(i).size(); j++)
      {
        incoming[adjList.get(i).get(j)] += 1;
      }
    }
    // Saves the topological order that is created
    ArrayList<Integer> topo = new ArrayList<>();
    // the queue to insert the vertices into the topo order with X taking priority
    LinkedList<Integer> q = new LinkedList<>();
    // the node that is being added to the q and topo
    int node;
    // a coint to determin if we didnt hit all the vertices or their is a cycle
    int cnt = 0;
    // find any node that we are able to start at with 0 incoming edges
    for (int i = 0; i < incoming.length; i++)
    {
      if (incoming[i] == 0)
      {
        q.add(i);
        // if we add the node to the queue then we make it have -1 incoming edges to let us know
        // that we have already used that node
        incoming[i]--;
      }
    }
    // Loops while their is vertexs still in the queue that is ready to be be put in the
    // topological order
    while(!q.isEmpty())
    {
      // If x is not in the q then it would not take priority
      if (q.indexOf(x - 1) == -1)
      {
        // y is in the q
        if (q.indexOf(y - 1) !=  -1)
        {
          // y is the only element in the q then it has to print out
          if (q.size() == 1)
          {
            node = q.remove();
            topo.add(node + 1);
          }
          // finds any vertex that is not Y that can be added to the topological order
          else
          {
            // if Y is the first element in the q then we just go to the next one
            if (q.indexOf(y - 1) == 0)
            {
              node = q.remove(1);
              topo.add(node + 1);
            }
            // if Y is not the first vertex then take out the first
            else
            {
              node = q.remove();
              topo.add(node + 1);
            }
          }
        }
        else
        {
          // removes from the head because the node we are looking for isnt in the queue yet
          node = q.remove();
          topo.add(node + 1);
        }
      }
      // if the node we are looking for is in the queue then we output it as soon as possible
      else
      {
        node = q.remove(q.indexOf(x - 1));
        topo.add(node + 1);
      }
      ++ cnt;
      // changes the incoming edge count for all the nodes that the vertex we just added points to
      for (int i = 0; i < adjList.get(node).size(); i++)
      {
        incoming[adjList.get(node).get(i)]--;
      }
      // after changing the incoming node see if any other vertexs now have 0 incoming nodes
      for (int i = 0; i < incoming.length; i++)
      {
        if (incoming[i] == 0)
        {
          q.add(i);
          incoming[i]--;
        }
      }
    }
    // didnt go to all the vertices or  their was a loop
    if (cnt != incoming.length)
    {
      return false;
    }
    // if X comes before Y then it will return true else it would return false
    if(topo.indexOf(x) < topo.indexOf(y))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public static double difficultyRating()
  {
    return 5.0;
  }

  public static double hoursSpent()
  {
    return 30.0;
  }
}
