const text = document.getElementById('text')
const form = document.getElementById('form')
const errorElement = document.getElementById('banner-container')

form.addEventListener('submit', (e) => {
  let messages = []
  if (text.value === '' || text.value == null) {
    messages.push('Required')
  }

  if (text.value.length <= 6) {
    messages.push('text must be longer than 6 characters')
  }

  if (text.value.length >= 20) {
    messages.push('text must be less than 20 characters')
  }

  if (text.value === 'text') {
    messages.push('Text cannot be text.')
  }

  if (messages.length > 0) {
    e.preventDefault()
    errorElement.innerText = messages.join(', ')
  }
})