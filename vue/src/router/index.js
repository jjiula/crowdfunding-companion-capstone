import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import CreateCampaignView from '../views/CreateCampaignView.vue';

import CampaignView from '../views/CampaignView.vue';
import SingleCampaignView from '../views/SingleCampaignView.vue';
import EditCampaignView from '../views/EditCampaignView.vue';
import ProposalsView from '../views/ProposalsView.vue';
import ProposalForm from '../components/ProposalForm.vue';
import FAQView from '../views/FAQview.vue';
import Donation from '../components/Donation.vue';
import CampaignsList from '../components/CampaignsList.vue';
import hallOfFame from '../views/HallOfFameView.vue';
import DonationHistoryView from '../views/DonationHistoryView.vue';
import TestView from '../views/TestView.vue';
import YourCampaignView from '../views/YourCampaignView.vue';



/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: '/',
    name: 'home',
    component: CampaignView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path:"/view/create-campaign",
    name: "campaignCreate",
    component: CreateCampaignView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path:"/view/all-campaigns",
    name: "all-campaigns",
    component: CampaignView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/view/campaign/:id",
    name: "SingleCampaignView",
    component: SingleCampaignView,
    meta: {
      requireAuth: true
    }
  },
  {
    path: "/view/campaign/:id/edit",
    name: "EditCampaign",
    component: EditCampaignView,
    meta: {
      requireAuth: true
    }
  },
  {
    path: "/view/campaign/:id/proposals",
    name: "ProposalList",
    component: ProposalsView,
    meta: {
      requireAuth: true
    }
  },
  {
    path:"/view/campaign/:id/makeProposal",
    name: "CreateProposal",
    component: ProposalForm,
    meta: {
      requireAuth: true
    }
  },
  {
    path: "/faq",
    name: "FAQ",
    component: FAQView
  },
  {
    path: "/view/campaign/:id/donate",
    name: "donation",
    component: Donation,
    meta: {
      requireAuth: true
    }
  },
  {
    path: "/hall-of-fame",
    name: "HallOfFame",
    component: hallOfFame,
    meta: {
      requireAuth: true
    }
  },
  {
    path:"/view/donation",
    name: "DonationHistory",
    component: DonationHistoryView,
    meta: {
      requireAuth: true
    }

  },

  {
    path:"/campaign/test",
    name: "campaigntest",
    component: TestView
  },
  {
    path: "/view/your-campaign",
    name: "yourCampaign",
    component: YourCampaignView,
    meta: {
      requireAuth: true
    }
  }
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
