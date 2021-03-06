package de.mpg.mpi.uima.engines.salie.pagerank.edges.structure

import de.mpg.mpi.uima.engines.salie.pagerank.graph.SalIEGraph

import scala.collection.JavaConverters._


class CliqueCreation extends GraphStructureCreation {


  /**
    * Connects nodes as a complete graph.
    *
    * @param salIEGraph
    */
  override def createEdges(salIEGraph: SalIEGraph) = {

    salIEGraph.getDirectedSparseGraph.getVertices.asScala.foreach(

      src => salIEGraph.getDirectedSparseGraph.getVertices.asScala
                .filter(dst => !src.equals(dst))
                .foreach(dst => {

                  // src -- [edgeID] --> dst
                  val edgeID = salIEGraph.getEdgeID(src, dst)
                  salIEGraph.getDirectedSparseGraph.addEdge(edgeID, src, dst)

                })

    )

  }
}
