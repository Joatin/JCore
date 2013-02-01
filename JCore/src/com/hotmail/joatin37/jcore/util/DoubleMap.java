/*
 * Copyright 2013 Joatin Granlund. All rights reserved.
 *
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 *    conditions and the following disclaimer.
 *
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list
 *    of conditions and the following disclaimer in the documentation and/or other materials
 *    provided with the distribution.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

package com.hotmail.joatin37.jcore.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DoubleMap<K, V1, V2> implements Map {

	private final Map<K, DoubleHolder> map;

	public DoubleMap(Map map) {
		this.map = map;
	}

	@Override
	public void clear() {
		this.map.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		Iterator<DoubleHolder> it = this.map.values().iterator();
		while (it.hasNext()) {
			if (it.next().containsValue(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set entrySet() {
		return this.map.entrySet();
	}

	@Override
	public V1 get(Object key) {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public Set keySet() {
		return this.map.keySet();
	}

	@Override
	public Object put(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(java.util.Map arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object remove(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection values() {
		// TODO Auto-generated method stub
		return null;
	}

	private class DoubleHolder {
		private V1 value1;

		private V2 value2;

		public V1 getValue1() {
			return this.value1;
		}

		public void setValue1(V1 value1) {
			this.value1 = value1;
		}

		public V2 getValue2() {
			return this.value2;
		}

		public void setValue2(V2 value2) {
			this.value2 = value2;
		}

		public boolean containsValue(Object value) {
			if (value == this.value1 || value == this.value2) {
				return true;
			} else {
				return false;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + this.getOuterType().hashCode();
			result = prime * result
					+ (this.value1 == null ? 0 : this.value1.hashCode());
			result = prime * result
					+ (this.value2 == null ? 0 : this.value2.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof DoubleMap.DoubleHolder)) {
				return false;
			}
			DoubleHolder other = (DoubleHolder) obj;
			if (!this.getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (this.value1 == null) {
				if (other.value1 != null) {
					return false;
				}
			} else if (!this.value1.equals(other.value1)) {
				return false;
			}
			if (this.value2 == null) {
				if (other.value2 != null) {
					return false;
				}
			} else if (!this.value2.equals(other.value2)) {
				return false;
			}
			return true;
		}

		private DoubleMap getOuterType() {
			return DoubleMap.this;
		}
	}
}
