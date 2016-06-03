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
		root = new Node(5);
		root.left = new Node(7);
		root.right = new Node(9);
		root.left.left = new Node(8);
		root.left.right = new Node(12);
		root.left.right.left = new Node(18);
		root.left.right.left.right = new Node(20);
		root.right.left = new Node(11);
		root.right.right = new Node(19);
		root.right.left.right = new Node(15);
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
	private int search(int[] inorder, int data, int start, int end) {
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
	}

}
