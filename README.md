<template>
  <div class="tree_box" :style="width && {width: width.includes('px') ? width : width + 'px'}">
    <el-select v-model="value" clearable filterable ref="treeSelect" :placeholder="placeholder || '请选择'" 
    :filter-method="selectFilter" @clear="clearSelected">
      <el-option :value="currentNodeKey" :label="currentNodeLabel">
        <el-tree-v2
          id="tree_v2"
          ref="treeV2"
         :data="options"
         :props="keyProps || TreeProps"
         :height="240"
         :current-node-key="currentNodeKey"
         @node-click="nodeClick"
         :expand-on-click-node="false"
         :filter-method="treeFilter"
        ></el-tree-v2>
      </el-option>
    </el-select>
  </div>
</template>


<script lang="ts">
import { TreeNode } from 'element-plus/es/components/tree-v2/src/types'
import { TreeNodeData } from 'element-plus/es/components/tree/src/tree.type'
import { defineComponent, nextTick, onMounted, PropType, reactive, ref, toRefs, watch } from 'vue'
interface PropsIter {
  value:string
  label:string
  children:string
  disabled?:boolean
}
const TreeProps:PropsIter = {
  value: 'id',
  label: 'label',
  children: 'children'
}
interface TreeIter {
  id: string
  label: string
  children?: TreeIter[]
}
export default defineComponent({
  name: 'tree select',
  props: {
  	// 组件绑定的options
    options: {
      type: Array as PropType<TreeIter[]>,
      required: true
    },
    // 配置选项
    keyProps: Object as PropType<PropsIter>,
    // 双向绑定值
    modelValue: [String, Number],
    // 组件样式宽
    width: String,
    // 空占位字符
    placeholder: String
  },
  setup (props, { emit }) {
    // 解决 props道具变异
    const { modelValue } = toRefs(props)
    const select:any = reactive({
      value: modelValue.value,
      currentNodeKey: '',
      currentNodeLabel: ''
    })
    const treeSelect = ref<HTMLElement | null>(null)
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const nodeClick = (data: TreeNodeData, node: TreeNode) => {
      select.currentNodeKey = data.id
      select.currentNodeLabel = data.label
      select.value = data.id;
      (treeSelect.value as any).blur()
      emit('update:modelValue', select.value)
    }
    // select 筛选方法 treeV2 refs
    const treeV2:any = ref<HTMLElement | null>(null)
    const selectFilter = (query:string) => {
      treeV2.value.filter(query)
    }
    // ztree-v2 筛选方法
    const treeFilter = (query:string, node: TreeNode) => {
      return node.label?.indexOf(query) !== -1
    }
    // 直接清空选择数据
    const clearSelected = () => {
      select.currentNodeKey = ''
      select.currentNodeLabel = ''
      select.value = ''
      emit('update:modelValue', undefined)
    }
    // setCurrent通过select.value 设置下拉选择tree 显示绑定的v-model值
    // 可能存在问题：当动态v-model赋值时 options的数据还没有加载完成就会失效，下拉选择时会警告 placeholder
    const setCurrent = () => {
      select.currentNodeKey = select.value
      treeV2.value.setCurrentKey(select.value)
      const data:TreeNodeData | undefined = treeV2.value.getCurrentNode(select.value)
      select.currentNodeLabel = data?.label
    }
    // 监听外部清空数据源 清空组件数据
    watch(modelValue, (v) => {
      if (v === undefined && select.currentNodeKey !== '') {
        clearSelected()
      }
      // 动态赋值
      if (v) {
        select.value = v
        setCurrent()
      }
    })
    // 回显数据
    onMounted(async () => {
      await nextTick()
      if (select.value) {
        setCurrent()
      }
    })
    return {
      treeSelect,
      treeV2,
      TreeProps,
      ...toRefs(select),
      nodeClick,
      selectFilter,
      treeFilter,
      clearSelected
    }
  }
})
</script>



<style lang="less" scoped>
.tree_box{
  width: 214px;
}
.el-scrollbar .el-scrollbar__view .el-select-dropdown__item {
  height: auto;
  max-height: 274px;
  padding: 0;
  overflow: hidden;
  overflow-y: auto;
}

.el-select-dropdown__item.selected {
  font-weight: normal;
}

ul li :deep(.el-tree .el-tree-node__content) {
  height: auto;
  padding: 0 20px;
}

.el-tree-node__label {
  font-weight: normal;
}

.el-tree :deep(.is-current .el-tree-node__label) {
  color: #409eff;
  font-weight: 700;
}

.el-tree :deep(.is-current .el-tree-node__children .el-tree-node__label) {
  color: #606266;
  font-weight: normal;
}
.selectInput {
  padding: 0 5px;
  box-sizing: border-box;
}
.el-select{
  width: 100% !important;
}
</style>


<com-tree-select :options="unitTreeOptions" v-model="searchForm.unitId" placeholder="单位名称" />
