package math;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		BigInteger number = new BigInteger();
		while (integer.charAt(0) == ' ')
		{
			integer = integer.substring(1, integer.length());
		}
		if (integer.charAt(0) == '-')
		{
			number.negative = true;
			integer = integer.substring(1, integer.length());
		}
		
		else if (integer.charAt(0) == '+')
		{
			integer = integer.substring(1,integer.length());
		}
		
		if (integer.length() > 0)
		{
			while ((integer.charAt(0) == ' ' || integer.charAt(0) == '0') && integer.length() > 1)
			{
				integer = integer.substring(1,integer.length());
			}
			while ((integer.charAt(integer.length()-1)) == ' ' && integer.length() > 1)
			{
				integer = integer.substring(0,integer.length()-1);
			}
			
			number.numDigits = integer.length();
			
			if (Character.isDigit(integer.charAt(integer.length()-1)) && integer.length() > 0)
			{
				int val = integer.charAt(0) - 48;
				number.front = new DigitNode(val, null);
				integer = integer.substring(1,integer.length());
				if (integer.length() > 0)
				{
					for (int i = 0; i < integer.length(); i++)
					{
						if (Character.isDigit(integer.charAt(i)))
						{
							val = integer.charAt(i) - 48;
							number.front = new DigitNode(val, number.front);
						}
						else
						{
							throw new IllegalArgumentException();
						}
					}
				}
				if (number.front.digit == 0 && number.negative == true && number.numDigits == 1)
				{
					number.negative = false;
				}
				return number;
			}
			else 
			{
				throw new IllegalArgumentException();
			}
		}
		
		else
		{
			throw new IllegalArgumentException();
		}
	}
		
	
	/**
	 * Adds an integer to this integer, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY this integer.
	 * NOTE that either or both of the integers involved could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param other Other integer to be added to this integer
	 * @return Result integer
	 */
	public BigInteger add(BigInteger other) {
		BigInteger sum = new BigInteger();
		sum.front = new DigitNode (0, null);
		DigitNode sumNode;
		DigitNode emptyNode;
		DigitNode valNode;
		DigitNode ptr1;
		DigitNode ptr2;
		int val;
		boolean zero = false;
		boolean remove = false;
		if (this.negative == other.negative)
		{
			if (this.negative == true)
			{
				sum.negative = true;
			}
			if (this.numDigits >= other.numDigits)
			{
				while (this.front != null)
				{
					if (other.front == null)
					{
						other.front = new DigitNode (0,null);
					}
					val = this.front.digit + other.front.digit;
					if (val >= 10)
					{
						val = val - 10;
						if (this.front.next == null)
						{
							valNode = new DigitNode (0, null);
							this.front.next = valNode;
						}
						if (other.front.next == null)
						{
							valNode = new DigitNode(0, null);
							other.front.next = valNode;
						}
						this.front.next.digit = this.front.next.digit + 1;
					}
					sumNode = new DigitNode (val, null);
					emptyNode = sum.front.next;
					sum.front.next = sumNode;
					sumNode.next = emptyNode;
					
					ptr1 = this.front.next;
					ptr2 = other.front.next;
					this.front = ptr1;
					other.front = ptr2;
					sum.numDigits++;
				}
			}
			
			else if (other.numDigits > this.numDigits)
			{
				while (other.front != null)
				{
					if (this.front == null)
					{
						this.front = new DigitNode (0,null);
					}
					val = this.front.digit + other.front.digit;
					if (val >= 10)
					{
						val = val - 10;
						if (other.front.next == null)
						{
							valNode = new DigitNode (0, null);
							other.front.next = valNode;
						}
						if (this.front.next == null)
						{
							valNode = new DigitNode(0, null);
							this.front.next = valNode;
						}
						other.front.next.digit = other.front.next.digit + 1;
					}
					sumNode = new DigitNode (val, null);
					emptyNode = sum.front.next;
					sum.front.next = sumNode;
					sumNode.next = emptyNode;
					
					ptr1 = this.front.next;
					ptr2 = other.front.next;
					this.front = ptr1;
					other.front = ptr2;
					sum.numDigits++;
				}
			}
			sum.front = sum.front.next;
		}
		else
		{
			int i = 1;
			int x;
			int y;
			boolean thisisBigger = false;
			boolean otherisBigger = false;
			boolean greater = false;
			boolean lesser = false;
			if (this.numDigits == other.numDigits)
			{
				ptr1 = this.front;
				ptr2 = other.front;
				while (ptr1 != null)
				{
					x = ptr1.digit * i;
					y = ptr2.digit * i;
					if (x > y)
					{
						thisisBigger = true;
						otherisBigger = false;
					}
					else if (x < y)
					{
						thisisBigger = false;
						otherisBigger = true;
					}
					ptr1 = ptr1.next;
					ptr2 = ptr2.next;
				}
				if (thisisBigger == true)
				{
					greater = true;
					if (this.negative == true)
					{
						sum.negative = true;
					}
				}
				else if (otherisBigger == true)
				{
					lesser = true;
					if (other.negative == true)
					{
						sum.negative = true;
					}
				}
			}
			if (this.numDigits > other.numDigits || greater == true)
			{
				if (this.numDigits > other.numDigits && this.negative == true)
				{
					sum.negative = true;
				}
				while (this.front != null)
				{
					if (other.front == null)
					{
						other.front = new DigitNode (0, null);
					}
					if (remove == true && this.front.digit >= 1)
					{
						this.front.digit = this.front.digit - 1;
						remove = false;
						zero = false;
					}
					if (zero == true && this.front.digit == 0)
					{
						this.front.digit = 9;
						remove = true;
					}
					val = this.front.digit - other.front.digit;
					
					if (this.front.digit < other.front.digit)
					{
						if (this.front.next.digit >= 1)
						{
							this.front.next.digit = this.front.next.digit - 1;
							val = 10 + this.front.digit - other.front.digit;
						}
						else if (this.front.next.digit == 0 && zero == false)
						{
							val = 10 + this.front.digit - other.front.digit;
							zero = true;
						}
						else if (this.front.next.digit == 0 && zero == true)
						{
							this.front.next.digit = 9;
							val = 9 + this.front.digit - other.front.digit;
						}
					}
					
					sumNode = new DigitNode (val, null);
					emptyNode = sum.front.next;
					sum.front.next = sumNode;
					sumNode.next = emptyNode;
					
					ptr1 = this.front.next;
					ptr2 = other.front.next;
					this.front = ptr1;
					other.front = ptr2;
				}
				while (sum.front.digit == 0 && sum.front.next != null)
				{
					sum.front = sum.front.next;
				}
				emptyNode = sum.front;
				while (emptyNode != null)
				{
					emptyNode = emptyNode.next;
					sum.numDigits++;
				}
			}
			
			else if (this.numDigits < other.numDigits || lesser == true)
			{
				if (other.negative == true)
				{
					sum.negative = true;
				}
				while (other.front != null)
				{
					if (this.front == null)
					{
						this.front = new DigitNode (0, null);
					}
					if (remove == true && other.front.digit >= 1)
					{
						other.front.digit = other.front.digit - 1;
						remove = false;
						zero = false;
					}
					if (zero == true && other.front.digit == 0)
					{
						other.front.digit = 9;
						remove = true;
					}
					val = other.front.digit - this.front.digit;
					
					if (other.front.digit < this.front.digit)
					{
						if (other.front.next.digit >= 1)
						{
							other.front.next.digit = other.front.next.digit - 1;
							val = 10 + other.front.digit - this.front.digit;
						}
						else if (other.front.next.digit == 0 && zero == false)
						{
							val = 10 + other.front.digit - this.front.digit;
							zero = true;
						}
						else if (other.front.next.digit == 0 && zero == true)
						{
							other.front.next.digit = 9;
							val = 9 + other.front.digit - this.front.digit;
						}
					}
					
					sumNode = new DigitNode (val, null);
					emptyNode = sum.front.next;
					sum.front.next = sumNode;
					sumNode.next = emptyNode;
					
					ptr1 = this.front.next;
					ptr2 = other.front.next;
					this.front = ptr1;
					other.front = ptr2;
				}
				while (sum.front.digit == 0 && sum.front.next != null)
				{
					sum.front = sum.front.next;
				}
				emptyNode = sum.front;
				while (emptyNode != null)
				{
					emptyNode = emptyNode.next;
					sum.numDigits++;
				}
			} 
		}
		DigitNode prev = null;
        DigitNode current = sum.front;
        DigitNode next = null;
        while (current != null)
        {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        sum.front = prev;
		return sum;
	}	
		// THE FOLLOWING LINE IS A PLACEHOLDER SO THE PROGRAM COMPILES
		// YOU WILL NEED TO CHANGE IT TO RETURN THE APPROPRIATE BigInteger
	// }
	
	/**
	 * Returns the BigInteger obtained by multiplying the given BigInteger
	 * with this BigInteger - DOES NOT MODIFY this BigInteger
	 * 
	 * @param other BigInteger to be multiplied
	 * @return A new BigInteger which is the product of this BigInteger and other.
	 */
	public BigInteger multiply(BigInteger other) {
		BigInteger product = new BigInteger();
		product.negative = false;
		product.numDigits = 1;
		product.front = new DigitNode (0, null);
		BigInteger response = new BigInteger();
		BigInteger value = new BigInteger();
		DigitNode ptr1;
		DigitNode ptr2;
		DigitNode number;
		DigitNode factor;
		DigitNode emptyNode;
		DigitNode filler;
		int multfac = 1;
		int multnum = 1;
		int val;
		int temp;
		if (this.numDigits >= other.numDigits)
		{
			number = this.front;
			factor = other.front;
		}
		else
		{
			number = other.front;
			factor = this.front;
		}
		ptr1 = factor;
		ptr2 = number;
		
		
		while (ptr1 != null)
		{
			while (ptr2 != null)
			{	
				temp = (ptr1.digit*multfac)*(ptr2.digit*multnum);
				value.front = new DigitNode(0, null);
				
				if (temp != 0)
				{
					while (temp != 0)
					{
						val = temp%10;
						temp = (temp - val)/10;
						
						filler = new DigitNode (val, null);
						emptyNode = value.front.next;
						value.front.next = filler;
						filler.next = emptyNode;
					}
					
				}
				
				while (value.front.digit == 0 && value.front.next != null)
				{
					value.front = value.front.next;
				}
				
				DigitNode prev = null;
		        DigitNode current = value.front;
		        DigitNode next = null;
		        while (current != null)
		        {
		            next = current.next;
		            current.next = prev;
		            prev = current;
		            current = next;
		        }
		        value.front = prev;
		        
		   
				multnum = multnum*10;
				ptr2 = ptr2.next;
	
				emptyNode = value.front;
				while (emptyNode != null)
				{
					value.numDigits++;
					emptyNode = emptyNode.next;
				}
					
				
				response = product.add(value);
				product.front = response.front;
				product = response;
				
				value = new BigInteger();
				
			}
			multfac = multfac*10;
			ptr1 = ptr1.next;
			ptr2 = number;
			multnum = 1;
		}

		if (this.negative == other.negative)
		{
			response.negative = false;
		}
		else
		{
			response.negative = true;
		}
		return response;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		
		return retval;
	}
	
}