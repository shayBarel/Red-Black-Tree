import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * RBTree_barelozana_amirskal
 * RBTree
 *
 * An implementation of a Red Black Tree with
 * non-negative, distinct integer keys and values
 *
 */

public class RBTree {


	public  int size;
	public RBNode root_sentinel;
	public RBNode max;
	public RBNode min;
	public RBNode nill;
	public int count_func;
	
	public RBTree(){
		size = 0;
		root_sentinel = new RBNode();
		root_sentinel.key = Integer.MAX_VALUE;
		max = new RBNode();
		min = new RBNode();
		nill=new RBNode();
		nill.key=-1;
		max.key = Integer.MIN_VALUE;
		min.key = Integer.MAX_VALUE;

	}
	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() {
		return (size==0);

	}
	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k) 
	{	
		if (empty())
		return null;
		
		RBNode x = root_sentinel.left;
		RBNode y = search_rec(x , k);			//helping function
			if ( y.key== -1)
				return null;
		return y.value;
	}
	/**
	 * public RBNode search_rec(RBNode x, int k)
	 *
	 * return RBNode which has key k.
	 *
	 */
	
	
	public RBNode search_rec(RBNode x, int k){ 
		if(x.key == -1||k==x.key)	
			return x;

		if ( k < x.key)
			return search_rec(x.left , k);
		else return search_rec(x.right , k);

	}

	/**
	 * public RBNode search_node(int k)
	 *
	 * return RBNode which has key k.
	 *
	 */
	public RBNode search_node(int k){
		if (empty())
			return null;
		RBNode x = root_sentinel.left;
		RBNode y = search_rec(x , k);
		if (y.key==-1) return null;
		return y;
	}
	/**
	 * public int insert(int k, String v)
	 *
	 * inserts an item with key k and value v to the red black tree.
	 * the tree must remain valid (keep its invariants).
	 * returns the number of color switches, or 0 if no color switches were necessary.
	 * returns -1 if an item with key k already exists in the tree.
	 */
	public int insert(int k, String v) {
		
		int colorCounter=0;
		RBNode z = new RBNode();
		z.key = k;
		z.value = v;

		RBNode x = root_sentinel.left;
		RBNode y = new RBNode();
		
		if ( search_node(k) != null){		//if the node already exist

			return -1; }
		
			
		if (empty()){
			root_sentinel.left=z;
			z.color=0;
			z.dad = root_sentinel;
			if (k>this.max.key){
				this.max=z;
			}
			if (k<this.min.key){
				this.min=z;
			}
			size = 1;
			return 0;} //no color switching was needed.
		if(z.key<min.key){
			min = z;
		}
		if (z.key>max.key){
			max = z;
		}
		while (x.key != -1){
			y=x;
			if (z.key < x.key)
				x = x.left;	
			else x = x.right;}
		z.dad = y;
		
		if (z.key < y.key) 
			y.left = z;
		else y.right = z;
		
		z.left = new RBNode();
		z.right = new RBNode();
		z.color = 1;
		y= new RBNode();
		while (z.dad.color == 1){ //insert fixup
			if (z.dad == z.dad.dad.left){ 
				
				y = z.dad.dad.right; //presuming z is a right child
				if (y.color == 1){  //case 1
					z.dad.color = 0;
					colorCounter++;
					y.color = 0;
					colorCounter++;
					z.dad.dad.color = 1;
					colorCounter++;
					z = z.dad.dad;

					}	
				else {
					if(z == z.dad.right){ //case 2
						z = z.dad;
						left_rotate(z);	}
					z.dad.color = 0;		//case 3
					colorCounter++;
					z.dad.dad.color = 1;
					colorCounter++;
					right_rotate(z.dad.dad);	}	}
			else {							//case 1
				y = z.dad.dad.left;
				if (y.color == 1){				//presuming z is a left child
					z.dad.color = 0;			
					colorCounter++;
					y.color = 0;
					colorCounter++;
					z.dad.dad.color = 1;
					colorCounter++;
					z = z.dad.dad;}	
				else {							//case 2
					if(z == z.dad.left){
						z = z.dad;
						right_rotate(z);	}
					z.dad.color = 0;			//case 3
					colorCounter++;
					z.dad.dad.color = 1;
					colorCounter++;
					left_rotate(z.dad.dad);	}	}
		}

		if (root_sentinel.left.color!=0) colorCounter++;
		root_sentinel.left.color=0;
		size++;

		return colorCounter;
	}
	
	/**
	 * public void left_rotate(RBNode x)
	 * 
	 * left rotate RBNode x exactly as seen in class
	 * 
	 *
	 */

	public void left_rotate(RBNode x){
		RBNode y  = x.right;
		x.right = y.left;
		if (y.left.key!=-1){
			y.left.dad=x;
		}
		y.dad=x.dad;
		if (x.dad.key==-1)
			root_sentinel.left=y;
		else if (x==x.dad.left) x.dad.left=y;
		else x.dad.right = y;
		y.left = x;
		x.dad = y;}
	
	/**
	 * public void right_rotate(RBNode x)
	 * 
	 * right rotate RBNode x exactly as seen in class
	 *
	 * 
	 */
	
	public void right_rotate(RBNode x){
		RBNode y = x.left;
		x.left = y.right;
		if (y.right.key!=-1){
			y.right.dad=x;
		}
		y.dad=x.dad;
		if (x.dad.key==-1)
			root_sentinel.left=y;
		else if (x==x.dad.right) x.dad.right=y;
		else x.dad.left = y;
		y.right = x;
		x.dad = y;

	}
	
	/**
	 * public void Transplant(RBNode u,RBNode v)
	 * 
	 * Transplant RBNode u and v.
	 *
	 */
	
	public void Transplant(RBNode u,RBNode v){
		if (u.dad.key==-1)
			root_sentinel.left=v;
		else if (u==u.dad.left)
			u.dad.left=v;
		else u.dad.right=v;
		v.dad = u.dad;
	}
	

	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there;
	 * the tree must remain valid (keep its invariants).
	 * returns the number of color switches, or 0 if no color switches were needed.
	 * returns -1 if an item with key k was not found in the tree.
	 */
	public int delete(int k){
		
		int colorCounter=0;
		RBNode y=new RBNode();
		RBNode w=new RBNode();
		RBNode x=new RBNode();
		RBNode z=search_node(k);
		
		if (z==null) return -1;
		
		if (z.key == max.key){	//replacing the max with predecessor
			
			if (size==1) {
				max = new RBNode();
				max.key = Integer.MIN_VALUE;
			}
			else{
			RBNode t = tree_pred(max);
			if (t.key==-1){
				max = new RBNode();
				max.key = Integer.MIN_VALUE;
			}
			else max=t;
		}}

		if (z.key == min.key){// replacing the min with the successor
			if (size==1) {
				min = new RBNode();
				min.key = Integer.MAX_VALUE;
			}
			else{
			RBNode t = tree_succesor(min);
			if (t.key==-1){
				min = new RBNode();
				min.key = Integer.MAX_VALUE;
			}
			else min=t;
		}}
		
		
		int original_color=z.color;
		y=z;
		if (z.left.key==-1){								
			x=z.right;
			Transplant(z,z.right); }
		else if (z.right.key==-1){
			x=z.left;
			Transplant(z,z.left);	
		}
		else {
			
			y = min_node(z.right);
			original_color=y.color;
			x=y.right;
			if (y.dad==z)
				x.dad=y;
			else{
				Transplant(y,y.right);
				y.right=z.right;
				y.right.dad=y;
			}

			Transplant(z,y);
			y.left=z.left;
			y.left.dad=y;
			if (y.color!=z.color){
			colorCounter++;
			}
			y.color=z.color;

		}
		if (original_color == 0){
			while (x != root_sentinel.left && x.color == 0){ //delete fixup.
				if (x.dad.left == x){
					w= x.dad.right;
					if (w.color == 1){ //case 1
						w.color=0;
						colorCounter += 1;
						x.dad.color=1;
						colorCounter += 1;
						left_rotate(x.dad);
						w = x.dad.right;
					}
					if (w.left.color == 0 && w.right.color == 0){ //case 2
						w.color = 1;
						colorCounter++;
						x=x.dad; }
					else {if (w.right.color == 0){			//case 3		//%&$$%&$%&$&
						w.left.color=0;
						colorCounter++;
						w.color=1;
						colorCounter++;
						right_rotate(w);
						w = x.dad.right;
					}
					//case 4
					if (w.color != x.dad.color) colorCounter++;
					w.color = x.dad.color;
					if (x.dad.color != 0) colorCounter++;
					x.dad.color = 0;
					if (w.right.color != 0) colorCounter ++;
					w.right.color = 0;
					left_rotate(x.dad);
					x = root_sentinel.left;	}}
				else{			//the same as before just switching left and right
					w= x.dad.left;
					if (w.color==1){               //case 1

						w.color=0;
						x.dad.color=1;
						colorCounter+=2;
						right_rotate(x.dad);
						w = x.dad.left;
					}
					if (w.left.color==0&&w.right.color==0){ //case 2
						w.color=1;
						colorCounter++;
						x=x.dad;
					}
					else {if (w.left.color==0){ //case 3
						w.right.color=0;
						colorCounter++;
						w.color=1;
						colorCounter++;
						left_rotate(w);
						w= x.dad.left;
					}						//case 4
					if( w.color != x.dad.color) colorCounter++;
					w.color=x.dad.color;
					if( x.dad.color!=0) colorCounter++;
					x.dad.color = 0;
					if (w.left.color!=0) colorCounter++;
					w.left.color=0;
					right_rotate(x.dad);
					x = root_sentinel.left;	
					}}
			}



		}
		if (x.color != 0) colorCounter++;
		x.color = 0;
		size--;
		return colorCounter;	
	}

	/**
	 * public RBNode tree_succesor(RBNode x)
	 *
	 * Returns the a RBNode which is the successor of RBNode x 
	 * same as seen on class.
	 * 
	 */


	public RBNode tree_succesor(RBNode x){
		if (x.right.key != -1){
			RBNode t = min_node(x.right);
			return t;
		}
		RBNode y;
		y = x.dad;
		while (y.key != -1 && x == y.right){
			x =y;
			y=y.dad;
		}
		return y;}
	
	/**
	 * public RBNode tree_pred(RBNode x)
	 *
	 * Returns the a RBNode which is the predecessor of RBNode x 
	 * Symmetric to successor.
	 * 
	 */


	public RBNode tree_pred(RBNode x){

		if (x.left.key != -1){
			RBNode t = max_node(x.left);
			return t;
		}
		RBNode y;
		y = x.dad;
		while (y.key !=-1 && x == y.left){
			x =y;
			y=y.dad;
		}
		return y;

	}
	
	/**
	 * public String min()
	 *
	 * Returns the value of the item with the smallest key in the tree,
	 * or null if the tree is empty
	 */

	public String min()
	{
		if (empty()) return null;
		return min.value; 
	}
	
	/**
	 * public RBNode min_node(RBNode tempo) 
	 *
	 * Returns the RBNode  with the smallest key in the tree,
	 * 
	 */

	public RBNode min_node(RBNode tempo) 
	{
		RBNode x = tempo;
		while (x.left.key != -1){
			x = x.left;
		}

		return x; 
	}
	/**
	 * public String max()
	 *
	 * Returns the value of the item with the largest key in the tree,
	 * or null if the tree is empty
	 */
	public String max()
	{
		
		if (empty()) return null;
		return max.value;
	}
	
	/**
	 * public RBNode max_node(RBNode tempo) 
	 *
	 * Returns the RBNode  with the highest key in the tree,
	 * 
	 */
	
	

	public RBNode max_node(RBNode tempo)
	{
		RBNode x = tempo;
		while (x.right.key!=-1){
			x = x.right;
		}

		return x; 
	}
	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree,
	 * or an empty array if the tree is empty.
	 */
	public int[] keysToArray()// 
	
	{
		
		count_func = 0;
		if (empty()) return new int[0];
		if (size == 1) {
			int[] returnedArr = {max.key};
			return returnedArr;
		}
		int[] arr = new int[size];
		arr = Inorder_walk(arr,root_sentinel.left);
		return arr;
		
	}
	
	/**
	 * public String[] Inorder_walk(String[] arr,RBNode x)
	 *
	 * Returns a sorted(by keys) String array which contains all values in the tree,
	 * 
	 */
	
public String[] Inorder_walk(String[] arr,RBNode x){ // Inorder walking on the tree leaves.return String array.
			  
	        if (x.key!=-1){ 
	        	Inorder_walk(arr,x.left); 
	            arr[count_func]= x.value;
	            count_func++;
	            Inorder_walk(arr,x.right); 
	            
	  
	  
	  
	        } 
	        return arr; 
	    }

/**
 * public int[] Inorder_walk(int[] arr,RBNode x)
 *
 * Returns a sorted(by keys) int array which contains all keys in the tree.
 * 
 */

public int[] Inorder_walk(int[] arr,RBNode x){ // Inorder walking on the tree leaves. returns int array.
	  
    if (x.key!=-1){
    	
    	Inorder_walk(arr,x.left); 
        arr[count_func]=x.key;
        count_func++;
        Inorder_walk(arr,x.right); 
        


    } 
    return arr; 
}
			
	
	
	/**
	 * public String[] valuesToArray()
	 *
	 * Returns an array which contains all values in the tree,
	 * sorted by their respective keys,
	 * or an empty array if the tree is empty.
	 */
	public String[] valuesToArray()						
	{
		count_func=0;
		if (empty()) return new String[0];
		if (size==1) {
			String[] yy= {max.value};
			return yy;
		}
		String[] arr = new String[size]; 
		arr = Inorder_walk(arr,root_sentinel.left);
		return arr;
		
	                
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none
	 * postcondition: none
	 */
	public int size()
	{
		return size; 
	}


	/**
	 * public class RBNode
	 *
	 * If you wish to implement classes other than RBTree
	 * (for example RBNode), do it in this file, not in 
	 * another file.
	 * This is an example which can be deleted if no such classes are necessary.
	 */

	
	
	
	public class RBNode{

		public int key;
		public String value;
		public RBNode dad;
		public RBNode left;
		public RBNode right;
		public int color; // "0" is for black , and "1" for red//


		public RBNode(){ //creates new node with NILL leaves
			this.key = -1;
			this.color = 0;
			this.right = nill;
			this.left = nill;
			this.value = null;
			this.dad = null;}
		




	}

}


