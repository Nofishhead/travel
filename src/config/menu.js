export default [
  {
    path: '/admin/dashboard',
    name: 'Dashboard',
    meta: {
      title: '控制台',
      icon: 'Odometer'
    }
  },
  {
    path: '/admin/scenic',
    name: 'Scenic',
    meta: {
      title: '景点管理',
      icon: 'Location'
    },
    children: [
      {
        path: '/admin/scenic/list',
        name: 'ScenicList',
        meta: {
          title: '景点列表'
        }
      },
      {
        path: '/admin/scenic/category',
        name: 'ScenicCategory',
        meta: {
          title: '景点分类'
        }
      }
    ]
  },
  {
    path: '/admin/food',
    name: 'Food',
    meta: {
      title: '美食管理',
      icon: 'Bowl'
    },
    children: [
      {
        path: '/admin/food/list',
        name: 'FoodList',
        meta: {
          title: '美食列表'
        }
      },
      {
        path: '/admin/food/category',
        name: 'FoodCategory',
        meta: {
          title: '美食分类'
        }
      }
    ]
  },
  {
    path: '/admin/route',
    name: 'Route',
    meta: {
      title: '线路管理',
      icon: 'Guide'
    },
    children: [
      {
        path: '/admin/route/list',
        name: 'RouteList',
        meta: {
          title: '线路列表'
        }
      },
      {
        path: '/admin/route/order',
        name: 'RouteOrder',
        meta: {
          title: '线路预订'
        }
      }
    ]
  },
  {
    path: '/admin/news',
    name: 'News',
    meta: {
      title: '新闻管理',
      icon: 'Document'
    },
    children: [
      {
        path: '/admin/news/list',
        name: 'NewsList',
        meta: {
          title: '新闻列表'
        }
      },
      {
        path: '/admin/news/category',
        name: 'NewsCategory',
        meta: {
          title: '新闻分类'
        }
      }
    ]
  },
  {
    path: '/admin/message',
    name: 'Message',
    meta: {
      title: '留言管理',
      icon: 'ChatDotRound'
    }
  },
  {
    path: '/admin/link',
    name: 'Link',
    meta: {
      title: '友情链接',
      icon: 'Link'
    }
  },
  {
    path: '/admin/user',
    name: 'User',
    meta: {
      title: '用户管理',
      icon: 'User'
    }
  }
]
