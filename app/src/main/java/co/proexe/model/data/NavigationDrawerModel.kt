package co.proexe.model.data

data class NavigationDrawerModel(
    val logoUrl: String = "https://www.dropbox.com/s/iucc4txh70jc8xe/logo%201.png?dl=1",
    val accountInfo: AccountInfo,
    val drawerItem: List<NavigationDrawerItem>
)