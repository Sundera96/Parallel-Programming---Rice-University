package edu.coursera.parallel;

import static edu.rice.pcdp.PCDP.forseq2d;

import edu.rice.pcdp.PCDP;
import edu.rice.pcdp.ProcedureInt1D;

/**
 * Wrapper class for implementing matrix multiply efficiently in parallel.
 */
public final class MatrixMultiply {
    /**
     * Default constructor.
     */
    private MatrixMultiply() {
    }

    /**
     * Perform a two-dimensional matrix multiply (A x B = C) sequentially.
     *
     * @param A An input matrix with dimensions NxN
     * @param B An input matrix with dimensions NxN
     * @param C The output matrix
     * @param N Size of each dimension of the input matrices
     */
    public static void seqMatrixMultiply(final double[][] A, final double[][] B,
            final double[][] C, final int N) {
        forseq2d(0, N - 1, 0, N - 1, (i, j) -> {
            C[i][j] = 0.0;
            for (int k = 0; k < N; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        });
    }

    /**
     * Perform a two-dimensional matrix multiply (A x B = C) in parallel.
     *
     * @param A An input matrix with dimensions NxN
     * @param B An input matrix with dimensions NxN
     * @param C The output matrix
     * @param N Size of each dimension of the input matrices
     */
    public static void parMatrixMultiply(final double[][] A, final double[][] B,
            final double[][] C, final int N) {
        /*
         * TODO Parallelize this outermost two-dimension sequential loop to
         * achieve performance improvement.
         */
    	int core = Runtime.getRuntime().availableProcessors();
    	int chunkSize=N/core;
    	
//    	PCDP.forall2d(0,core, 0, N - 1, (i, j) -> {
//    		int chunkstart = i*(chunkSize);
//    		int chunkEnd = (i+1)*(chunkSize);
//    		if(chunkEnd>N)
//    			chunkEnd=N;
//    		for(int index=chunkstart;index<chunkEnd;index++) {
//    			C[index][j] = 0.0;
//                for (int k = 0; k < N; k++) {
//                    C[index][j] += A[index][k] * B[k][j];
//                }
//    		}
//            
//        });
    	
    	PCDP.forall(0,core,(block)-> {
    		System.out.println(block);
    		int chunkstart = block*(chunkSize);
    		int chunkEnd = (block+1)*(chunkSize);
    		if(chunkEnd>N)
    			chunkEnd=N;
    		forseq2d(chunkstart,chunkEnd-1, 0, N - 1, (i, j) -> {
                C[i][j] = 0.0;
                for (int k = 0; k < N; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            });
    		
           });
       }
    	
    	
}
