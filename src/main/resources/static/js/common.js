document.addEventListener('DOMContentLoaded', () => {
	document.getElementById('signoutLink').addEventListener('click', () => {
		document.getElementById('signoutForm').submit();
	})
	
	Array.from(document.getElementsByClassName('userStatusChangeLink')).forEach(link => link.addEventListener('click', () => {
		
		document.getElementById('changeUserId').setAttribute('value', link.getAttribute('data-id'));
		document.getElementById('changeUserStatus').setAttribute('value', link.getAttribute('data-status'));
		
		document.getElementById('changeUserName').innerText = link.getAttribute('data-user');
		document.getElementById('changeUserStatusBefore').innerText = link.getAttribute('data-status') == 'true' ? 'Active' : 'Suspend';
		document.getElementById('changeUserStatusAfter').innerText = link.getAttribute('data-status') == 'true' ? 'Suspend' : 'Active';
		
		const dialog = new bootstrap.Modal('#userStatusChangeModal');
		dialog.show();
	}))
	
	const pageSizeChangeSelect = document.getElementById('pageSizeChangeSelect');
	if (pageSizeChangeSelect) {
		pageSizeChangeSelect.addEventListener('change', () => {
			const formId = pageSizeChangeSelect.getAttribute('data-form-id');
			const size = pageSizeChangeSelect.value;
			
			const form = document.getElementById(formId);
			
			const sizeInput = document.createElement('input');
			sizeInput.setAttribute('type', 'hidden');
			sizeInput.setAttribute('name', 'size');
			sizeInput.setAttribute('value', size);
			
			form.appendChild(sizeInput);
			
			form.submit();
		})
	}
	
})