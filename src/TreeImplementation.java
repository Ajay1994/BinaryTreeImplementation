import java.util.*;
class Node{
	int data;
	Node left;
	Node right;
	Node(int data){
		this.data = data;
		this.left = null;
		this.right = null;
	}
}
class BinaryTree{
	Node root;
	BinaryTree(){
		/*root = new Node(5);
		root.left = new Node(7);
		root.right = new Node(9);
		root.left.left = new Node(8);
		root.left.right = new Node(12);
		root.left.right.left = new Node(18);
		root.left.right.left.right = new Node(20);
		root.left.right.right = new Node(20);
		root.right.left = new Node(11);
		root.right.right = new Node(19);
		root.right.left.right = new Node(15);
		//root.left.right.left.right.left = new Node(99);*/
		root = new Node(-15);
		root.left = new Node(5);
		root.right = new Node(6);
		root.left.left = new Node(-8);
		root.left.right = new Node(1);
		root.left.left.left = new Node(2);
		root.left.left.right = new Node(6);
		root.right.left = new Node(3);
		root.right.right = new Node(9);
		root.right.right.right = new Node(0);
		root.right.right.right.left = new Node(4);
		root.right.right.right.right = new Node(-1);
		root.right.right.right.right.left = new Node(10);
	}
	public void inorder(Node root){
		if(root == null) return;
		inorder(root.left);
		//Solve the left tree first then print the root
		System.out.print(root.data +" ->");
		inorder(root.right);
	}
	public void preorder(Node root){
		if(root == null) return;
		System.out.print(root.data +" ->");
		preorder(root.left);
		preorder(root.right);
	}
	public void postorder(Node root){
		if(root == null) return;
		postorder(root.left);
		postorder(root.right);
		System.out.print(root.data +" ->");
		
	}
	public void levelOrder(){
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		queue.add(new Node(-1));
		while(!queue.isEmpty()){
			Node temp = queue.remove();
			if(temp.data == -1){
				queue.add(new Node(-1));
				temp = queue.remove();
				if(temp.data == -1) break;
				System.out.println();
			}
			System.out.print(temp.data +" ->");
			if(temp.left != null) queue.add(temp.left);
			if(temp.right != null) queue.add(temp.right);
		}
	}
	public void inorderWithoutRec(Node root){
		Stack<Node> stack = new Stack<Node>();
		if(root == null) return;
		do{
			while(root != null){
				stack.push(root);
				root = root.left;
			}
			//Pop till we get a node whose right is not null
			while(!stack.isEmpty() && stack.peek().right == null){
				Node x = stack.pop();
				System.out.print(x.data + " ->");
			}
			//Check if all the elements have been popped and stack is m=not empty
			if(stack.isEmpty()) break;
			Node x = stack.pop();
			System.out.print(x.data + " ->");
			root = x.right;
		}while(root != null);
	}
	public int getSize(Node root){
		if(root == null) return 0;
		int leftSize = getSize(root.left);
		int rightSize = getSize(root.right);
		return 1+leftSize+rightSize;
	}
	public boolean isIdentical(Node root1, Node root2){
		if(root1 == null && root2 == null) return true;
		boolean isLeftIdentical = isIdentical(root1.left, root2.left);
		boolean isRightIdentical = isIdentical(root1.right, root2.right);
		return (isLeftIdentical && isRightIdentical && (root1.data == root2.data));
	}
	public int max(int x , int y){
		if(x>=y) return x;
		else return y;
	}
	public int maxDepth(Node root){
		if(root == null) return 0;
		int leftHieght = maxDepth(root.left);
		int rightHeight = maxDepth(root.right);
		return max(rightHeight, leftHieght)+1;
	}
	public void makeMirror(Node root){
		if(root == null) return;
		Node temp = root.left;
		root.left = root.right;
		root.right = temp;
		makeMirror(root.left);
		makeMirror(root.right);
	}
	static int preIndex = 0;
	public int search(int[] inorder, int data, int start, int end) {
		System.out.println(start + "-> "+end);
		for(int i = start; i <= end; i++ ){
			if(inorder[i] == data) return i;
		}
		return -1;
	}
	public Node makeTreeFromTraversals(int[] preorder, int[] inorder, int start, int end){
		if(start > end) return null;
		if(start == end ) {
			 return new Node(preorder[preIndex++]);
		}
		int loc = search(inorder, preorder[preIndex], start, end);
		System.out.println(preorder[preIndex] + "=" + loc);
		Node node = new Node(preorder[preIndex++]);
		node.left = makeTreeFromTraversals(preorder, inorder, start, loc -1);
		node.right = makeTreeFromTraversals(preorder, inorder, loc +1, end);
		return node;
	}
	public void printRootToleafPath(Node root, int[] path, int index){
		//check if this is the leaf node then print the tree
		if(root.left == null && root.right == null){
			path[index] = root.data;
			for(int i = 0; i <= index ; i++){
				System.out.print(path[i]+ " -> ");
			}
			System.out.println();
			return;
		}
		path[index] = root.data;
		if(root.left != null) printRootToleafPath(root.left, path, index+1);
		if(root.right != null) printRootToleafPath(root.right, path, index+1);
	}
	public int countLeafNode(Node root){
		if(root == null) return 0;
		if(root.left == null && root.right == null) return 1;
		int leftCount = countLeafNode(root.left);
		int rightCount = countLeafNode(root.right);
		return leftCount+rightCount;
	}
	public void levelOrderSpiral(Node root){
		Stack<Node> stack1 = new Stack<Node>();
		Stack<Node> stack2 = new Stack<Node>();
		stack1.push(root);
		while(true){
			if(stack2.isEmpty() && stack1.isEmpty()) break;
			if(stack2.isEmpty()){
				while(!stack1.isEmpty()){
					Node node = stack1.pop();
					System.out.print(node.data + " -> ");
					if(node.left != null) stack2.push(node.left);
					if(node.right != null) stack2.push(node.right);
				}
			}else if(stack1.isEmpty()){
				if(stack1.isEmpty()){
					while(!stack2.isEmpty()){
						Node node = stack2.pop();
						System.out.print(node.data + " -> ");
						if(node.right != null) stack1.push(node.right);
						if(node.left != null) stack1.push(node.left);
					}
				}
			}
		}
	}
	public boolean childrenSumProperty(Node root){
		//For every node, data value must be equal to sum of data values in left and right children. 
		if(root == null) return true;
		if(root.left == null && root.right == null) return true;
		if(root.left == null){
			boolean isRightTrue = childrenSumProperty(root.right);
			return (root.data == root.right.data) && isRightTrue;
		}
		if(root.right == null){
			boolean isLeftTrue = childrenSumProperty(root.left);
			return (root.data == root.left.data) && isLeftTrue;
		}
		boolean isRightTrue = childrenSumProperty(root.right);
		boolean isLeftTrue = childrenSumProperty(root.left);
		boolean toReturn = (root.data == (root.right.data+root.left.data)) && isRightTrue && isLeftTrue;
		//System.out.println("Root : "+ root.data + " type:"+ toReturn);
		return toReturn;
	}
	public Node convertWithChildrenSumProperty(Node root){
		if(root == null) return root;
		if(root.left == null && root.right == null) return root;
		Node left = convertWithChildrenSumProperty(root.right);
		Node right = convertWithChildrenSumProperty(root.left);
		if(root.left == null){
			root.data = left.data;
			return root;
		}
		if(root.right == null){
			root.data = right.data;
			return root;
		}
		root.data = (left.data+right.data);
		return root;
	}
	public int getDiameter(Node root){
		if(root == null) return 0;
		if(root.left == null && root.right == null) return 1;
		int leftDiameter = getDiameter(root.left);
		int rightDiameter = getDiameter(root.right);
		int maxDiameter = leftDiameter > rightDiameter ? leftDiameter : rightDiameter;
		return max(maxDiameter, maxDepth(root.left)+ maxDepth(root.right) + 1);
	}
	public boolean pathOfgivenSumExists(Node root, int sum){
		if(root == null && sum != 0) return false;
		if(root == null && sum == 0) return true;
		System.out.println(sum + " : "+ root.data);
		boolean inLeft = pathOfgivenSumExists(root.left, sum - root.data);
		boolean inRight = pathOfgivenSumExists(root.right, sum - root.data);
		return (inLeft || inRight);
	}
	/*
	 * To create Double tree of the given tree, create a new duplicate for each node, and insert the 
	 * duplicate as the left child of the original node.
	 * So the tree…
		
		    2
		   / \
		  1   3
		
		is changed to…
		
		       2
		      / \
		     2   3
		    /   /
		   1   3
		  /
		 1

	 */
	public Node makeDoubleTree(Node root){
		if(root == null) return null;
		
		Node transformedTreeLeft = makeDoubleTree(root.left);
		Node transformedTreeRight = makeDoubleTree(root.right);
		root.left = new Node(root.data);
		root.left.left = transformedTreeLeft;
		root.right = transformedTreeRight;
		return root;
	}
	/*
	 * Given a binary tree, write a function to get the maximum width of the given tree. 
	 * Width of a tree is maximum of widths of all levels.
	 * Solution 1: The Queue based level order traversal will take O(n) time in worst case.
	 * Solution 2: create a temporary array count[] of size equal to the height of tree. We initialize all 
	 * values in count as 0. We traverse the tree using preorder traversal and fill the entries in count so
	 * that the count array contains count of nodes at each level in Binary Tree. 
	 */
	static int[] levelWidth;
	public int getWidth(Node root){
		if(root == null) return 0;
		if(root.left == null && root.right == null) return 1;
		else{
			levelWidth = new int[maxDepth(root)];
			for(int i = 0; i < levelWidth.length; i++) levelWidth[i] = 0;
			fillLevelWidth(0, root);
			return getMax(levelWidth);
		}
	}
	private int getMax(int[] array) {
		// TODO Auto-generated method stub
		if(array.length == 0) return Integer.MIN_VALUE;
		int max = array[0];
		for(int i =0; i< array.length; i++){
			max = max(max, array[i]);
		}
		return max;
	}
	private void fillLevelWidth(int level, Node root) {
		// TODO Auto-generated method stub
		if(root == null) return;
		if(root.left == null && root.right == null){
			levelWidth[level] += 1;
			return;
		}
		levelWidth[level] += 1;
		fillLevelWidth(level+1, root.left);
		fillLevelWidth(level+1, root.right);
	}
	/*
	 * A tree can be folded if left and right subtrees of the tree are structure wise mirror image of each other. An empty tree is considered as foldable.

		Consider the below trees:
		(a) and (b) can be folded.
		
		(a)
		       10
		     /    \
		    7      15
		     \    /
		      9  11
		
		(b)
		        10
		       /  \
		      7    15
		     /      \
		    9       11
		1) If tree is empty, then return true.
		2) Convert the left subtree to its mirror image
		    mirror(root->left); /* See this post 
		3) Check if the structure of left subtree and right subtree is same
		   and store the result.
		    res = isStructSame(root->left, root->right); /*isStructSame()
		        recursively compares structures of two subtrees and returns
		        true if structures are same 
		4) Revert the changes made in step (2) to get the original tree.
		    mirror(root->left);
		5) Return result res stored in step 2.

	 */
	
	public boolean isFoldable(Node root){
		if(root == null) return true;
		if(root.left == null && root.right == null) return true;
		Node node = getMirror(root.left);
		return isStructralSimilar(node, root.right);
	}
	private boolean isStructralSimilar(Node node1, Node node2) {
		// TODO Auto-generated method stub
		if(node1 == null && node2 == null) return true;
		if(node1 != null && node2 != null){
			boolean isLeft = isStructralSimilar(node1.left, node2.left);
			boolean isRight = isStructralSimilar(node1.right, node2.right);
			if(isLeft && isRight) return true;
		}
		return false;
	}
	private Node getMirror(Node node) {
		// TODO Auto-generated method stub
		if(node == null) return null;
		Node temp = node.left;
		node.left = node.right;
		node.right = temp;
		makeMirror(node.left);
		makeMirror(node.right);
		return node;
	}
	public int getNodeLevel(Node root, int level, int data){
		if(root == null) return 0;
		if(root.data == data) return level;
		int inLeftLevel = getNodeLevel(root.left, level + 1, data);
		if(inLeftLevel != 0) return inLeftLevel;
		int inRightLevel = getNodeLevel(root.right, level+1, data);
		return inRightLevel;
	}
	public boolean printAncesstorNode(Node root, int data){
		if(root == null) return false;
		if(root.data == data){
			System.out.print(root.data +" ->");
			return true;
		}
		boolean left = printAncesstorNode(root.left, data);
		boolean right = printAncesstorNode(root.right, data);
		if(left || right){
			System.out.print(root.data +" ->");
			return true;
		}
		return false;
	}
	/*
	 * Check For SUM Tree : A SumTree is a Binary Tree where the value of a node is equal to sum of the nodes 
	 * present in its left subtree and right subtree. An empty tree is SumTree and sum of an empty tree can be 
	 * considered as 0. A leaf node is also considered as SumTree.
	 */
	public int isSumTree(Node root){
		if(root == null) return 0;
		if(root.left == null && root.right == null) return root.data;
		int leftSum = 0;
		int rightSum = 0;
		if(root.left != null) leftSum = isSumTree(root.left);
		if(root.right != null) rightSum = isSumTree(root.right);
		if(leftSum == Integer.MIN_VALUE || rightSum == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		if(root.data == leftSum + rightSum) return 2*root.data;
		else return Integer.MIN_VALUE;
	}
	/*
	 * Given a Binary Tree where each node has positive and negative values. Convert this to a tree where each node 
	 * contains the sum of the left and right sub trees in the original tree. The values of leaf nodes are changed to 0.

		For example, the following tree
		
		                  10
		               /      \
			     -2        6
		           /   \      /  \ 
			 8     -4    7    5
		
		should be changed to
		
		                 20(4-2+12+6)
		               /      \
			   4(8-4)      12(7+5)
		           /   \      /  \ 
			 0      0    0    0

	 */
	public int makeSumTree(Node root){
		if(root == null) return 0;
		//Here we have to process first left and right and after that the root.
		int leftSum = makeSumTree(root.left);
		int rightSum = makeSumTree(root.right);
		int rootOldSum = root.data;
		root.data = leftSum + rightSum;
		return rootOldSum + root.data;
	}
	/*
	 * Vertical Sum in a given Binary Tree
	 * Given a Binary Tree, find vertical sum of the nodes that are in same vertical line. Print all sums through different vertical lines.

		Examples:
		
		      1
		    /   \
		  2      3
		 / \    / \
		4   5  6   7
		
		The tree has 5 vertical lines
		
		Vertical-Line-1 has only one node 4 => vertical sum is 4
		Vertical-Line-2: has only one node 2=> vertical sum is 2
		Vertical-Line-3: has three nodes: 1,5,6 => vertical sum is 1+5+6 = 12
		Vertical-Line-4: has only one node 3 => vertical sum is 3
		Vertical-Line-5: has only one node 7 => vertical sum is 7
		
		So expected output is 4, 2, 12, 3 and 7.
		Here we can do with the pre order traversal. 
	 */
	static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	public void saveVerticleSum(Node root, int level){
		if(root == null) return;
		if(map.containsKey(level)){
			map.put(level, map.get(level) + root.data) ;
		}else{
			map.put(level, root.data);
		}
		saveVerticleSum(root.left, level + 1);
		saveVerticleSum(root.right, level - 1);
	}
	public void printVerticle(Node root){
		saveVerticleSum(root, 0);
		Set<Integer> set = map.keySet();
		for (Integer key : set) {
			System.out.println("Level : "+ key + " && Sum :" + map.get(key));
		}
	}
	static int maxSum = 0;
	public void maxSumTillLeaf(Node root, int sum){
		if(root == null) return;
		if(root.left == null && root.right == null){
			maxSum = max(sum + root.data ,maxSum);
			return;
		}
		maxSumTillLeaf(root.left, sum + root.data);
		maxSumTillLeaf(root.right, sum + root.data);
	}
	/*
	 * Boundary Traversal of binary tree
	 * 
	 */
	public void printBoundaryTraversal(Node root){
		if(root == null) return;
		System.out.println(root.data + " -> ");
		printLeftBoundary(root.left);
		printleafNodes(root.left);
		printleafNodes(root.right);
		printRightBoundary(root.right);
	}
	private  void printleafNodes(Node root) {
		// TODO Auto-generated method stub
		if(root == null) return;
		if(root.left == null && root.right == null){
			System.out.println(root.data + " -> ");
			return;
		}
		printleafNodes(root.left);
		printleafNodes(root.right);
	}
	private  void printRightBoundary(Node root) {
		// TODO Auto-generated method stub
		//here we have to go in top down fashion, so call recursion, then print
		if(root == null) return;
		if(root.right != null){
			printRightBoundary(root.right);
			System.out.println(root.data + " -> ");
		}
		else if(root.left != null){
			printRightBoundary(root.left);
			System.out.println(root.data + " -> ");
		}else{
			//Leaf condition so avoid them printing.
		}
		return;
	}
	private static void printLeftBoundary(Node root) {
		// TODO Auto-generated method stub
		if(root == null) return;
		if(root.left != null){
			System.out.println(root.data + " -> ");
			printLeftBoundary(root.left);
		}
		else if(root.right != null){
			System.out.println(root.data + " -> ");
			printLeftBoundary(root.right);
		}else{
			//Leaf condition so avoid them printing.
		}
		return;
	}
}
public class TreeImplementation {
	
	public static void main(String[] args) {
		
		BinaryTree tree = new BinaryTree(); 
		System.out.println("\n______________________________Check For Preorder Traversal _________________________\n");
		tree.preorder(tree.root);
		System.out.println();
		
		System.out.println("\n______________________________Check For Inorder Traversal _________________________\n");
		tree.inorder(tree.root);
		System.out.println();
		
		System.out.println("______________________________Check For Postorder Traversal _________________________\n");
		tree.postorder(tree.root);
		System.out.println();
		
		System.out.println("\n______________________________Check For Level Order Traversal _________________________\n");
		tree.levelOrder();
		System.out.println();
		
		System.out.println("\n______________________________Check For Inorder Without Recursion Traversal _________________________\n");
		tree.inorderWithoutRec(tree.root);
		System.out.println();
		
		System.out.println("\n______________________________Check For Size Of Tree _________________________\n");
		System.out.println("Size of tree is : "+tree.getSize(tree.root));
		System.out.println();
		
		System.out.println("\n______________________________Check For Identicality Of Trees _________________________\n");
		BinaryTree tree2 = new BinaryTree();
		BinaryTree tree1 = new BinaryTree();
		System.out.println("Is Identical Tree : "+ tree1.isIdentical(tree1.root, tree2.root));
		
		System.out.println("\n______________________________Check For Depth Of Tree _________________________\n");
		System.out.println("Depth of tree is : "+tree.maxDepth(tree.root));
		System.out.println();
		
		System.out.println("\n______________________________Check For Mirror Of Tree _________________________\n");
		tree.makeMirror(tree.root);
		tree.inorder(tree.root);
		System.out.println();
		
		System.out.println("\n______________________________Check For Construction from Preorder and Inorder _________________________\n");
		int [] preorder = {5 , 7 ,8 ,12 ,18 ,20 ,9 ,11 ,15 ,19};
		int [] inorder = {8 ,7 ,18 ,20 ,12 ,5 ,11 ,15 ,9 ,19};
		Node root = tree.makeTreeFromTraversals(preorder, inorder, 0, inorder.length -1);
		tree.preorder(root);
		System.out.println();
		
		System.out.println("\n______________________________Check For All paths from root_________________________\n");
		tree = new BinaryTree();
		int[] path = new int[10];
		tree.printRootToleafPath(tree.root, path, 0);
		System.out.println();
		
		
		System.out.println("\n______________________________Check For Count Of Nodes in Tree _________________________\n");
		System.out.println("Count of Leaf Nodes of tree is : "+tree.countLeafNode(tree.root));
		System.out.println();
		
		
		System.out.println("\n______________________________Check For Spiral Level Order Traversal Tree _________________________\n");
		tree.levelOrderSpiral(tree.root);
		System.out.println();
		
		System.out.println("\n______________________________Check For Children Sum Property Of Tree _________________________\n");
		System.out.println("Is Children SUm property True of tree is : "+tree.childrenSumProperty(tree.root));
		System.out.println();
		
		System.out.println("\n______________________________Check For Conversion to Children Sum Property Of Tree _________________________\n");
		Node temp = tree.convertWithChildrenSumProperty(tree.root);
		tree.inorder(tree.convertWithChildrenSumProperty(tree.root));
		System.out.println();
		
		System.out.println("\n______________________________Check For Diameter Of Tree _________________________\n");
		System.out.println("Diameter of tree is : "+tree.getDiameter(tree.root));
		System.out.println();
		
		System.out.println("\n______________________________Check For path of given sum Of Tree _________________________\n");
		tree = new BinaryTree();
		System.out.println("Is there path of given sum : "+tree.pathOfgivenSumExists(tree.root, 40));
		System.out.println();
		
		System.out.println("\n______________________________Check For Making of Double tree _________________________\n");
		tree = new BinaryTree();
		tree.inorder(tree.root);
		System.out.println();
		Node newRoot = tree.makeDoubleTree(tree.root);
		tree.inorder(newRoot);
		System.out.println();
		
		System.out.println("\n______________________________Check For width Of Tree _________________________\n");
		tree = new BinaryTree();
		System.out.println("Width of Tree is  : "+tree.getWidth(tree.root));
		System.out.println();
		
		System.out.println("\n______________________________Check For Foldabilty Of Tree Of Tree _________________________\n");
		tree = new BinaryTree();
		System.out.println("Foldabilty of Tree is  : "+tree.isFoldable(tree.root));
		System.out.println();
		
		System.out.println("\n______________________________Check For getting Node level of  Tree _________________________\n");
		tree = new BinaryTree();
		System.out.println("Level of node in tree is  : "+tree.getNodeLevel(tree.root, 1, 20));
		System.out.println();
		
		System.out.println("\n______________________________Check For printing ancesstor of a node Tree _________________________\n");
		tree = new BinaryTree();
		System.out.println("Ancesstor of node is  : "+tree.printAncesstorNode(tree.root, 15));
		System.out.println();
		
		
		System.out.println("\n______________________________Check For Sum Tree _________________________\n");
		tree = new BinaryTree();
		if(tree.isSumTree(tree.root) == Integer.MIN_VALUE){
			System.out.println("Not a Sum Tree");
		}else{
			System.out.println("Sum Tree");
		}
		System.out.println();
		
		System.out.println("\n______________________________Check For Making a Sum Tree _________________________\n");
		tree = new BinaryTree();
		tree.makeSumTree(tree.root);
		tree.inorder(tree.root);
		System.out.println();
		
		System.out.println("\n______________________________Check For printing verticle sum_________________________\n");
		tree = new BinaryTree();
		tree.printVerticle(tree.root);
		System.out.println();
		
		System.out.println("\n______________________________Check for maximum sum till now_________________________\n");
		tree = new BinaryTree();
		tree.maxSumTillLeaf(tree.root,  0);
		System.out.println("Max Sum : "+tree.maxSum);
		System.out.println();
		
		System.out.println("\n______________________________Check for tree Biundary Traversal_________________________\n");
		tree = new BinaryTree();
		tree.printBoundaryTraversal(tree.root);
	}

}
