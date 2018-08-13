package de.mpg.mpi.uima.engines.salie.ranking.pagerank.edges.structure

import de.mpg.mpi.uima.engines.salie.ranking.pagerank.graph.SalIEOpenFactGraph

trait GraphStructureCreation {


  /**
    * Creates edges in salIEGraph.graph.
    *
    * @param salIEGraph
    */
  def createEdges(salIEGraph: SalIEOpenFactGraph)

}
