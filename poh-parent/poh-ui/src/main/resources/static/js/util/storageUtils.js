function StorageOperation(storage) {
    this.storage = storage

    this.saveString = function (key, value) {
        this.storage.setItem(key, value)
    }

    this.getString = function (key) {
        return this.storage.getItem(key)
    }

    this.saveObject = function (key, value) {
        this.storage.setItem(key, JSON.stringify(value))
    }

    this.getObject = function (key) {
        return JSON.parse(this.storage.getItem(key))
    }

    this.remove = function (key) {
        return this.storage.removeItem(key)
    }

    this.sessionKeyIsEmpty = function (key) {
        let val = this.getString(key)
        return val != null && val !== ''
    }
}

const storageUtils = {
    LOCAL: new StorageOperation(localStorage),
    SESSION: new StorageOperation(sessionStorage)
}

const getCurrentUser = () => {
    return storageUtils.LOCAL.getObject(storageConstant.CURRENT_USER_TAG)
}

const getToken = () => {
    let currentUser = getCurrentUser()
    return currentUser.token
}
