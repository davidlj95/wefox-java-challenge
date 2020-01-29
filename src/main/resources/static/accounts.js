let accounts = [];

function findAccountById(accountId) {
    return accounts[findAccountKeyById(accountId)];
}

function findAccountKeyById(accountId) {
    for (let key = 0; key < accounts.length; key++) {
        if (accounts[key].id == accountId) {
            return key;
        }
    }
}

const apiUrl = '/api/v1/accounts/';
const accountService = {
    findAll(fn) {
        axios
            .get(apiUrl)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    findById(id, fn) {
        axios
            .get(apiUrl + id)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    findByEmail(email, fn) {
        axios
            .get(apiUrl + '?email=' + encodeURIComponent(email))
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    create(account, fn) {
        axios
            .post(apiUrl, account)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    update(id, account, fn) {
        axios
            .put(apiUrl + id, account)
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    delete(id, fn) {
        axios
            .delete(apiUrl + id)
            .then(response => {fn(response)})
            .catch(error => {console.log(error)})
    }
};

const AccountList = Vue.extend({
    template: '#account-list',
    data: function () {
        return {accounts: [], searchKey: ''};
    },
    computed: {
        filteredAccounts() {
            this.searchKey = this.searchKey.toLowerCase();
            return this.accounts.filter((account) => {
                return account.name.includes(this.searchKey)
                    || (!!account.email && account.email.toLowerCase().includes(this.searchKey))
                    || (!!account.age && account.age.toString().toLowerCase().includes(this.searchKey))
                    || (!!account.addresses && account.addresses.some(address => address.address.toLowerCase().includes(this.searchKey)))
            })
        }
    },
    mounted() {
        accountService.findAll(accountsResponse => {
                this.accounts = accountsResponse.data;
                accounts = accountsResponse.data
            }
        );
    }
});

const AccountView = Vue.extend({
    template: '#account',
    data: function () {
        return {account: findAccountById(this.$route.params.account_id)};
    }
});

const AccountEdit = Vue.extend({
    template: '#account-edit',
    data: function () {
        return {account: findAccountById(this.$route.params.account_id)};
    },
    methods: {
        updateAccount: function () {
            accountService.update(this.account.id, this.account, r => router.push('/'))
        },
        addAddress: function () {
            this.account.addresses.push({address: undefined});
        },
        removeAddress(index) {
            this.account.addresses.splice(index, 1)
        }
    }
});

const AccountDelete = Vue.extend({
    template: '#account-delete',
    data: function () {
        return {account: findAccountById(this.$route.params.account_id)};
    },
    methods: {
        deleteAccount: function () {
            accountService.delete(this.account.id, r => router.push('/'))
        }
    }
});

const AccountAdd = Vue.extend({
    template: '#account-add',
    data() {
        return {
            account: {name: '', email: '', age: undefined, addresses: []}
        }
    },
    methods: {
        addAddress() {
            this.account.addresses.push({address: undefined})
        },
        removeAddress(index) {
            this.account.addresses.splice(index, 1)
        },
        createAccount() {
            accountService.create(this.account, r => router.push('/'))
        }
    }
});

const router = new VueRouter({
    routes: [
        {
            path: '/',
            component: AccountList,
            name: 'account-list'
        },
        {
            path: '/add',
            component: AccountAdd,
            name: 'account-add'
        },
        {path: '/:account_id', component: AccountView, name: 'account'},
        {
            path: '/:account_id/edit',
            component: AccountEdit,
            name: 'account-edit'
        },
        {
            path: '/:account_id/delete',
            component: AccountDelete,
            name: 'account-delete'
        }
    ]
});

new Vue({
    router
}).$mount('#app');
