
public class Percolation {
	private int size;
	private boolean[] opened;
	private int[] flat;
	WeightedQuickUnionUF wqu;
	
	public Percolation(int N) {
		this.size = N;
		this.opened = new boolean[N*N + 2];
		this.flat = new int[N*N + 2];
		wqu = new WeightedQuickUnionUF(N*N + 2);
		for(int k = 0; k < N*N+2; k++) {
			flat[k] = k;
			opened[k] = false;
		}
	}              
	
	private int xyTo1D(int i, int j) {
		if ((i > this.size || i < 1) || (j > this.size || j < 1)) {
    		throw new IndexOutOfBoundsException();
    	}
		return this.flat[(i-1)*size + j];
	}
	
	// open site (row i, column j) if it is not already
    public void open(int i, int j) {
    	if ((i > this.size || i < 1) || (j > this.size || j < 1)) {
    		throw new IndexOutOfBoundsException();
    	}
    	int flat = this.xyTo1D(i, j);
		this.opened[flat] = true;
		if (i > 1 && j > 1 && i < this.size && j < this.size) {
			if (this.isOpen(i-1, j)) {
				int top = this.xyTo1D(i-1, j);
				wqu.union(flat, top);
			}
			if (this.isOpen(i+1, j)) {
				int bottom = this.xyTo1D(i+1, j);
				wqu.union(flat, bottom);
			}
			if (this.isOpen(i, j+1)) {
				int right = this.xyTo1D(i, j+1);
				wqu.union(flat, right);
			}
			if (this.isOpen(i, j-1)) {
				int left = this.xyTo1D(i, j-1);
				wqu.union(flat, left);
			}
		}
		else if (i == 1) {
			int virtualTop = this.flat[0];
			wqu.union(flat, virtualTop);
		}
		else if (i == this.size && j == 1) {
			int virtualBottom = this.flat[this.size*this.size+1];
			wqu.union(flat, virtualBottom);
			if (this.isOpen(i-1, j)) {
				int top = this.xyTo1D(i-1, j);
				wqu.union(flat, top);
			}
			if (this.isOpen(i, j+1)) {
				int right = this.xyTo1D(i, j+1);
				wqu.union(flat, right);
			}
		}
		else if (i == this.size && j == this.size) {
			int virtualBottom = this.flat[this.size*this.size+1];
			wqu.union(flat, virtualBottom);
			if (this.isOpen(i-1, j)) {
				int top = this.xyTo1D(i-1, j);
				wqu.union(flat, top);
			}
			if (this.isOpen(i, j-1)) {
				int left = this.xyTo1D(i, j-1);
				wqu.union(flat, left);
			}
		}
		else if (i == this.size && j > 1 && j < this.size) {
			int virtualBottom = this.flat[this.size*this.size+1];
			wqu.union(flat, virtualBottom);
			if (this.isOpen(i-1, j)) {
				int top = this.xyTo1D(i-1, j);
				wqu.union(flat, top);
			}
			if (this.isOpen(i, j+1)) {
				int right = this.xyTo1D(i, j+1);
				wqu.union(flat, right);
			}
			if (this.isOpen(i, j-1)) {
				int left = this.xyTo1D(i, j-1);
				wqu.union(flat, left);
			}
		}
		else if (i > 1 && i < this.size && j == 1) {
			if (this.isOpen(i, j+1)) {
				int right = this.xyTo1D(i, j+1);
				wqu.union(flat, right);
			}
			if (this.isOpen(i-1, j)) {
				int top = this.xyTo1D(i-1, j);
				wqu.union(flat, top);
			}
			if (this.isOpen(i+1, j)) {
				int bottom = this.xyTo1D(i+1, j);
				wqu.union(flat, bottom);
			}
		}
		else if (i > 1 && i < this.size && j == this.size) {
			if (this.isOpen(i, j-1)) {
				int left = this.xyTo1D(i, j-1);
				wqu.union(flat, left);
			}
			if (this.isOpen(i-1, j)) {
				int top = this.xyTo1D(i-1, j);
				wqu.union(flat, top);
			}
			if (this.isOpen(i+1, j)) {
				int bottom = this.xyTo1D(i+1, j);
				wqu.union(flat, bottom);
			}
		}
    }     
    
	// is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
    	if ((i > this.size || i < 1) || (j > this.size || j < 1)) {
    		throw new IndexOutOfBoundsException();
    	}
    	int flated = this.xyTo1D(i, j);
    	if (this.opened[flated])
    		return true;
    	else
    		return false;
    } 

	// is site (row i, column j) full?
    public boolean isFull(int i, int j) {
    	if ((i > this.size || i < 1) || (j > this.size || j < 1)) {
    		throw new IndexOutOfBoundsException();
    	}
    	int site = this.xyTo1D(i, j);
    	return wqu.connected(site, this.flat[0]);
    }    
	
	// does the system percolate?
    public boolean percolates() {
    	if (wqu.connected(this.flat[0], this.flat[this.size*this.size+1])) {
    		return true;
    	}
    	return false;
    }         

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
