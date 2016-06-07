import java.util.*;

class NNode{
	String data;
	NNode left;
	NNode right;
	NNode(String str){
		this.data = str;
		this.left = null;
		this.right = null;
	}
}
class RBUtil{
	int max;
	int min;
	boolean isBalanced;
	RBUtil(int max, int min, boolean isTrue){
		this.max = max;
		this.min = min;
		this.isBalanced = isTrue;
	}
}
class Tree extends BinaryTree{
	Tree(){
		super();
	}
	static int index = 0;
	public Node createTreePreorderAndPostorder(int[] preorder, int[] postorder, int start, int end){
		if(start > end) return null;
		if(start == end){
			return new Node(preorder[index++]);
		}
		Node root = new Node(preorder[index++]);
		int loc = search(postorder, preorder[index], start, end);
		if(loc != -1){
			root.left = createTreePreorderAndPostorder(preorder, postorder, start, loc);
			root.right = createTreePreorderAndPostorder(preorder, postorder, loc+1, end-1);
		}
		return root;
	}
	public void preorderIterative(Node root){
		Stack<Node> stack = new Stack<Node>();
		stack.push(root);
		while(!stack.isEmpty()){
			Node node = stack.pop();
			System.out.println(node.data + " ->");
			if(node.right != null) stack.push(node.right);
			if(node.left != null) stack.push(node.left);
		}
	}
	public int getLargestIndependentSet(Node root){
		if(root == null) return 0;
		int LISChildren = getLargestIndependentSet(root.left)+ getLargestIndependentSet(root.right);
		int LISgrandChildren = 0;
		if(root.left != null){
			LISgrandChildren += getLargestIndependentSet(root.left.right);
			LISgrandChildren += getLargestIndependentSet(root.left.left);
		}
		if(root.right != null){
			LISgrandChildren += getLargestIndependentSet(root.right.right);
			LISgrandChildren += getLargestIndependentSet(root.right.left);
		}
		return max(LISChildren , (LISgrandChildren + 1));
	}
	private static final String[] alphabet = {"", "a", "b", "c", "d", "e",
	        "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
	        "s", "t", "u", "v", "w", "x", "v", "z"};
	public NNode createTreeWithString(int[] array, int indexStart, int indexEnd, String parent){
		NNode root = null;
		if(indexEnd == -1){
			String data = alphabet[0];
			root = new NNode(parent + data);
			parent = data;
		}else{
			if(indexStart >= array.length || indexEnd >= array.length) return null;
			if(indexStart == indexEnd){
				String data = alphabet[array[indexStart]];
				//System.out.println("PO :" + parent);
				root = new NNode(parent + data);
				//System.out.println(root.data);
				parent = root.data;
			}else{
				if(array[indexStart] * 10 + array[indexEnd] > 26) return null;
				//System.out.println("PO :" + parent);
				String data = alphabet[(array[indexStart] * 10 + array[indexEnd])];
				root = new NNode( parent + data);
				//System.out.println(root.data);
				parent = root.data;
			}
		}
		root.left = createTreeWithString(array, indexEnd + 1, indexEnd + 1, parent);
		root.right = createTreeWithString(array, indexEnd + 1, indexEnd + 2, parent);
		return root;
	}
	public void traverseNNode(NNode root){
		if(root == null) return;
		traverseNNode(root.left);
		if(root.left == null && root.right == null){
			System.out.println(root.data);
		}
		traverseNNode(root.right);
	}
	public boolean treeIsomorphism(Node root1, Node root2){
		if(root1.data != root2.data) return false;
		if(root1 == null && root2 == null) return true;
		if(root1 == null || root2 == null) return false;
		
		return ((treeIsomorphism(root1.left, root2.left) && treeIsomorphism(root1.right, root2.right)) ||
				treeIsomorphism(root1.left, root2.right) && treeIsomorphism(root1.right, root2.left));
	}
	public int getOddLevelMaxDepth(Node root, int level){
		if(root == null) return 0;
		if(root.left == null && root.right == null){
			System.out.println(level+" "+ root.data);
			if(level % 2 == 0) return 0;
			else return level;
		}
		int leftMax = getOddLevelMaxDepth(root.left, level+1);
		int rightMax = getOddLevelMaxDepth(root.right, level + 1);
		return max(leftMax, rightMax);
	}
	public boolean isAllLeafSameLevel(Node root, int height, int level){
		if(root == null) return true;
		if(root.left == null && root.right == null){
			if(level == height) return true;
			else return false;
		}
		boolean isLeftTrue = isAllLeafSameLevel(root.left, height, level+1);
		boolean isRightTrue = isAllLeafSameLevel(root.right, height, level+1);
		//what will root return after it evaluated about the left and right child
		//Similar to post order traversal that we have values returned from left and right portion, now draw
		//conclusion for the current root.
		return isLeftTrue && isRightTrue;
	}
	static int max_level = 0;
	public void printLeftViewTree(Node root, int level){
		if(root == null) return;
		if(max_level < level){
			System.out.print(root.data + " ->");
			max_level = level;
		}
		printLeftViewTree(root.left, level + 1);
		printLeftViewTree(root.right, level + 1);
	}
	
	static int m_level = 0;
	static int result = -1;
	public void maxDeepestLeftLeaf(Node root, boolean isLeft, int level){
		if(root == null) return;
		if(root.left == null && root.right == null){
			if(isLeft == true && level > m_level){
				result = root.data;
				m_level = level;
			}
			return;
		}
		maxDeepestLeftLeaf(root.left, true, level + 1);
		maxDeepestLeftLeaf(root.right, false, level+1);
	}
	static int sum = 0;
	public void sumAllNoFromRootToLeaf(Node root, int tempSum){
		if(root == null) return;
		if(root.left == null && root.right == null){
			tempSum = tempSum * 10 + root.data;
			Tree.sum += tempSum;
			System.out.println(tempSum);
			return;
		}
		tempSum = tempSum * 10 + root.data;
		sumAllNoFromRootToLeaf(root.left, tempSum);
		sumAllNoFromRootToLeaf(root.right, tempSum);
	}
	
	public Node getLCS(Node root, int data1, int data2){
		if(root == null) return null;
		// If either n1 or n2 matches with root's key, report
	    // the presence by returning root (Note that if a key is
	    // ancestor of other, then the ancestor key becomes LCA
		if(root.data == data1 || root.data == data2) return root;
		Node left = getLCS(root.left, data1, data2);
		Node right = getLCS(root.right, data1, data2);
		//check if both left and right say it to be null, then return root
		if(left != null && right != null) return root;
		//else look for the which part said yes and return that
		else return left != null ? left : right;
	}
	
	static int d1Len = -1;
	static int d2Len = -1;
	static int lca = -1;
	public Node distanceBetweenTwoKeys(Node root, int data1, int data2, int level){
		if(root == null) return null;
		if(root.data == data1){
			d1Len = level;
			lca = level;
			return root;
		}
		if(root.data == data2){
			d2Len = level;
			lca = level;
			return root;
		}
		Node left = distanceBetweenTwoKeys(root.left, data1, data2, level+1);
		Node right = distanceBetweenTwoKeys(root.right, data1, data2, level+1);
		
		if(left != null && right != null){
			lca = level;
			return root;
		}else return left != null ? left : right;
	}
	
	public void nodesAtKDistance(Node root, int k, int[] path, int pathlen){
		if(root == null) return;
		if(root.left == null && root.right == null){
			path[pathlen++] = root.data;
			
			System.out.println(path[pathlen - k - 1]);
			return;
		}
		path[pathlen++] = root.data;
		nodesAtKDistance(root.left, k, path, pathlen);
		nodesAtKDistance(root.right, k, path, pathlen);
	}
	public int min(int left, int right) {
		return left > right ? right : left;
	}
	/*
	 * Check if a given Binary Tree is height balanced like a Red-Black Tree
	 */
	
	public RBUtil checkHeightBalanced(Node root){
		if(root == null){
			return new RBUtil(0,0, true);
		}
		RBUtil left = checkHeightBalanced(root.left);
		RBUtil right = checkHeightBalanced(root.right);
		
		if(left.isBalanced == false) return left;
		if(right.isBalanced == false) return right;
		
		int max = max(left.max, right.max) + 1;
		int min = min(left.min, right.min) + 1;
		
		if(max <= 2 * min){
			return new RBUtil(max, min, true);
		}else return new RBUtil(max, min, false);
	}
	/*
	 * Find the maximum path sum between two leaves of a binary tree
	 */
	static int max_sum_path = 0;
	public int maximumSumPathLeafs(Node root){
		if(root == null) return 0;
		int leftSum = maximumSumPathLeafs(root.left);
		int rightSum = maximumSumPathLeafs(root.right);
		
		if(max_sum_path < leftSum + root.data + rightSum){
			System.out.println(root.data + " " + leftSum + " " + rightSum);
			max_sum_path = leftSum + root.data + rightSum;
		}
		return max(leftSum + root.data, rightSum + root.data);
	}
	/*
	 * Check if two nodes are cousins in a Binary Tree
	 * Two nodes are cousins of each other if they are at same level and have different parents.
	 */
	static int datalevel1 = 0;
	static int datalevel2 = 0;
	static Node parent1 = null;
	static Node parent2 = null;
	public void isCousinNodes(Node root, int data1 , int data2, int level, Node p1, Node p2 ){
		if(root == null) return;
		if(root.data == data1) {
			parent1 = p1;
			datalevel1 = level;
		}
		if(root.data == data2) {
			parent2 = p2;
			datalevel2 = level;
		}
		isCousinNodes(root.left, data1, data2, level + 1, root, root);
		isCousinNodes(root.right, data1, data2, level + 1, root, root);
	}
	/*
	 * 
		Find the closest leaf in a Binary Tree
		
		Given a Binary Tree and a key ‘k’, find distance of the closest leaf from ‘k’.
		
		Examples:
		
		              A
		            /    \    
		           B       C
		                 /   \  
		                E     F   
		               /       \
		              G         H
		             / \       /
		            I   J     K
		
		Closest leaf to 'H' is 'K', so distance is 1 for 'H'
		Closest leaf to 'C' is 'B', so distance is 2 for 'C'
		Closest leaf to 'E' is either 'I' or 'J', so distance is 2 for 'E' 
	 */
	public int findClosestLeafDown(Node root, int level){
		if(root == null) return Integer.MAX_VALUE;
		if(root.left == null && root.right == null) return level;
		int left = findClosestLeafDown(root.left, level + 1);
		int right = findClosestLeafDown(root.right, level + 1);
		return min(left, right);
	}
	List<Node> ancesstors = new ArrayList<Node>();
	public boolean getClosestLeaf(Node root, int key, int level){
		if(root == null) return false;
		if(root.data == key){
			ancesstors.add(root);
			return true;
		}
		boolean isLeft = getClosestLeaf(root.left, key, level + 1);
		boolean isRight = getClosestLeaf(root.right, key, level + 1);
		
		if(isLeft || isRight){
			ancesstors.add(root);
			return true;
		}else return false;
	}
	public void printClosestLeafUtil(Node root, int data){
		getClosestLeaf(root, data, 1);
		int min_length = 0;
		Node key = ancesstors.remove(0);
		int keyDownLeaf = findClosestLeafDown(root, 0);
		min_length = keyDownLeaf;
		for(int i = 1; i <= ancesstors.size(); i++){
			int down = findClosestLeafDown(ancesstors.get(i - 1), 0);
			if(down + i < min_length) {
				System.out.println("Through ANcesstor : "+ ancesstors.get(i - 1).data);
				min_length = down + i;
			}
		}
		System.out.println("Minimum path is : "+ min_length);
	}
	
	/*
	 * Removal of all the half nodes i.e nodes with one child 
	 * 
	 */
	public Node removeHalfNodes(Node root){
		if(root == null) return null;
		if(root.left == null && root.right == null) return root;
		Node left = removeHalfNodes(root.left);
		Node right = removeHalfNodes(root.right);
		if(left != null && right != null){
			root.left = left;
			root.right = right;
			return root;
		}
		if(right == null){
			root.left = left;
			return left;
		}
		else{
			root.right = right;
			return right;
		}
	}
	
	public int vertexCoverProblem(Node root){
		if(root == null) return 0;
		if(root.left == null && root.right == null) return 0;
		
		//If root is part of Vertex Cover
		int ifPart = 1 + vertexCoverProblem(root.left) + vertexCoverProblem(root.right);
		
		//If root is not part of Cover then its two childs will be
		int ifNotPart = 0;
		if(root.left != null)
			ifNotPart += vertexCoverProblem(root.left.left) + vertexCoverProblem(root.left.right);
		if(root.right != null)
			ifNotPart += vertexCoverProblem(root.right.left) + vertexCoverProblem(root.right.right);
		
		return min(ifPart , ifNotPart+2);
	}
}
public class BinaryTreeImplementation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("\n______________________________Check For Construction from Preorder and Postorder _________________________\n");
		Tree tree = new Tree();
		int [] preorder = {5 , 7 ,8 ,12 ,18 ,20 ,9 ,11 ,15 ,19};//8 ->7 ->18 ->20 ->12 ->5 ->11 ->15 ->9 ->19 ->
		int [] postorder = {8 ,20 ,18 ,12 ,7 ,15 ,11 ,19, 9, 5};
		Node root = tree.createTreePreorderAndPostorder(preorder, postorder, 0, postorder.length -1);
		tree.inorder(root);
		System.out.println();
		
		System.out.println("\n______________________________Check For Iterative Preorder Traversal_________________________\n");
		tree = new Tree();
		tree.preorderIterative(tree.root);
		
		System.out.println("\n______________________________Check For maximum Independent Set_________________________\n");
		tree = new Tree();
		System.out.println("Maximum Independent Set is : "+tree.getLargestIndependentSet(tree.root));
		
		System.out.println("\n______________________________Check For all possible interpretation of digits_________________________\n");
		int [] array = {1,2,1};
		NNode root1 = tree.createTreeWithString(array, -1, -1, "");
		//System.out.println(root1.data);
		tree.traverseNNode(root1);
		
		System.out.println("\n______________________________Check For maximum depth odd level_________________________\n");
		tree = new Tree();
		System.out.println("Maximum Depth : "+ tree.getOddLevelMaxDepth(tree.root, 1));
		
		System.out.println("\n______________________________Check For if all the leaves at same level_________________________\n");
		tree = new Tree();
		int height = 0;
		Node temp = tree.root;
		while(temp != null){
			height += 1;
			temp = temp.left;
		}
		System.out.println("Height is maximum : "+ height);
		System.out.println("Is true : "+ tree.isAllLeafSameLevel(tree.root, height, 1));
		
		System.out.println("\n______________________________Check For printing left level of tree_________________________\n");
		tree = new Tree();
		tree.printLeftViewTree(tree.root, 1);
		
		System.out.println("\n______________________________Check For deepest left leaf of tree_________________________\n");
		tree = new Tree();
		tree.maxDeepestLeftLeaf(tree.root, true, 1);
		System.out.println("Result :" + Tree.result);
		
		System.out.println("\n______________________________Check For allpath num sum tree_________________________\n");
		tree = new Tree();
		tree.sumAllNoFromRootToLeaf(tree.root, 0);
		System.out.println("Result :" + Tree.sum);
		
		System.out.println("\n______________________________Check For LCA of tree_________________________\n");
		tree = new Tree();
		Node lca = tree.getLCS(tree.root, 20, 15);
		//System.out.println("Result :" + lca.data);
		
		System.out.println("\n______________________________Check For distance between two keys_________________________\n");
		tree = new Tree();
		tree.distanceBetweenTwoKeys(root, 15, 19, 1);
		System.out.println(tree.d1Len);
		System.out.println(tree.d2Len);
		System.out.println(tree.lca);
		System.out.println("Distance is : " + (tree.d1Len + tree.d2Len - 2 * tree.lca));
		
		System.out.println("\n______________________________Check For nodes at distance k_________________________\n");
		tree = new Tree();
		tree.nodesAtKDistance(tree.root, 0, new int[10], 0);
		
		System.out.println("\n______________________________Check For if tree is height balanced like RB Tree_________________________\n");
		tree = new Tree();
		RBUtil result = tree.checkHeightBalanced(tree.root);
		System.out.println("Is True : "+ result.isBalanced);
		
		System.out.println("\n______________________________Check For maximum path sum between leaf nodes Tree_________________________\n");
		tree = new Tree();
		tree.maximumSumPathLeafs(tree.root);
		System.out.println("Result : "+ Tree.max_sum_path);
		
		System.out.println("\n______________________________Check For cousine node of Tree_________________________\n");
		tree = new Tree();
		tree.isCousinNodes(tree.root, 1, 6, 1, null, null);
		//System.out.println(Tree.parent1.data + " " + Tree.parent2.data);
		if(Tree.datalevel1 == Tree.datalevel2 && Tree.parent1 != Tree.parent2 ){
			System.out.println("Yes .. COusins !!");
		}else{
			System.out.println("No .. Not Cousins !!");
		}
		
		System.out.println("\n______________________________Check For closest leaf Down_________________________\n");
		tree = new Tree();
		System.out.println(tree.root.data);
		System.out.println(tree.findClosestLeafDown(tree.root, 1));
		
		System.out.println("\n______________________________Check For closest leaf _________________________\n");
		tree = new Tree();
		tree.printClosestLeafUtil(tree.root, 0);
		
		System.out.println("\n______________________________Check For removal of half nodes _________________________\n");
		tree = new Tree();
		tree.inorder(tree.root);
		Node newRoot = tree.removeHalfNodes(tree.root);
		System.out.println();
		tree.inorder(newRoot);
		
		
		System.out.println("\n______________________________Check For Vertex Cover problem DP_________________________\n");
		tree = new Tree();
		System.out.println("Vertex Cover Problem is : "+tree.vertexCoverProblem(tree.root));
	}

}
